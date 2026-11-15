package com.dev7ex.multiwarp.warp;

import com.dev7ex.multicommon.api.configuration.Configuration;
import com.dev7ex.multicommon.api.configuration.ConfigurationProperties;
import com.dev7ex.multicommon.api.configuration.ConfigurationProvider;
import com.dev7ex.multicommon.api.configuration.FileConfiguration;
import com.dev7ex.multicommon.api.configuration.JsonConfiguration;
import com.dev7ex.multiwarp.api.MultiWarpApi;
import com.dev7ex.multiwarp.api.warp.Warp;
import com.dev7ex.multiwarp.api.warp.WarpLocation;
import com.dev7ex.multiwarp.api.warp.WarpProperty;
import com.dev7ex.multiwarp.api.warp.WarpRepository;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
@ConfigurationProperties(fileName = "warp.json", provider = JsonConfiguration.class)
public class DefaultWarpRepository extends Configuration implements WarpRepository {

    private static final String WORLD_KEY = "world";
    private static final String X_KEY = "x";
    private static final String Y_KEY = "y";
    private static final String Z_KEY = "z";
    private static final String YAW_KEY = "yaw";
    private static final String PITCH_KEY = "pitch";

    private final MultiWarpApi multiWarpApi;

    public DefaultWarpRepository(@NotNull final MultiWarpApi multiWarpApi) {
        super(multiWarpApi);
        this.multiWarpApi = multiWarpApi;
    }

    @Override
    public void saveFile() {
        final File file = super.getConfigurationFile();
        final File temporaryFile = new File(file.getParentFile(), file.getName() + ".tmp");

        try {
            ConfigurationProvider.getProvider(super.getProvider()).save(super.getFileConfiguration(), temporaryFile);

            try {
                Files.move(temporaryFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);

            } catch (final AtomicMoveNotSupportedException exception) {
                Files.move(temporaryFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (final IOException exception) {
            throw new UncheckedIOException(String.format("Can't save File %s", file.getName()), exception);

        } finally {
            temporaryFile.delete();
        }
    }

    @Override
    public void save(@NotNull final Warp warp) {
        this.checkKey(warp.getName());

        this.applyWithRollback(Collections.singletonMap(warp.getName(), this.serializeWarp(warp)));
    }

    @Override
    public void rename(@NotNull final String oldName, @NotNull final Warp warp) {
        this.checkKey(oldName);
        this.checkKey(warp.getName());

        final Map<String, Object> changes = new LinkedHashMap<>();
        changes.put(oldName, null);
        changes.put(warp.getName(), this.serializeWarp(warp));
        this.applyWithRollback(changes);
    }

    @Override
    public void delete(@NotNull final String name) {
        this.checkKey(name);

        this.applyWithRollback(Collections.singletonMap(name, null));
    }

    @Override
    public boolean contains(@NotNull final String name) {
        return (this.isValidKey(name)) && (super.getFileConfiguration().contains(name));
    }

    @Override
    public @NotNull Optional<Warp> load(@NotNull final String name) {
        if (!this.isValidKey(name)) {
            this.multiWarpApi.getLogger().warning(String.format("Skipping Warp %s: invalid storage key", name));
            return Optional.empty();
        }

        if (!(super.getFileConfiguration().get(name) instanceof final FileConfiguration section)) {
            if (this.contains(name)) {
                this.multiWarpApi.getLogger().warning(String.format("Skipping Warp %s: invalid format", name));
            }
            return Optional.empty();
        }

        if ((!(section.get(WarpProperty.LOCATION.getKey()) instanceof final FileConfiguration locationSection))
                || (!(locationSection.get(DefaultWarpRepository.WORLD_KEY) instanceof final String worldName))
                || (!this.hasRequiredCoordinates(locationSection))) {
            this.multiWarpApi.getLogger().warning(String.format("Skipping Warp %s: invalid location", name));
            return Optional.empty();
        }

        try {
            final WarpLocation location = WarpLocation.builder()
                    .setWorldName(worldName)
                    .setX(locationSection.getDouble(DefaultWarpRepository.X_KEY))
                    .setY(locationSection.getDouble(DefaultWarpRepository.Y_KEY))
                    .setZ(locationSection.getDouble(DefaultWarpRepository.Z_KEY))
                    .setYaw(locationSection.getFloat(DefaultWarpRepository.YAW_KEY))
                    .setPitch(locationSection.getFloat(DefaultWarpRepository.PITCH_KEY))
                    .build();

            final String displayName = section.getString(WarpProperty.DISPLAY_NAME.getKey(), name);

            return Optional.of(this.multiWarpApi.getWarpFactory().createWarp(name,
                    ((displayName == null) || (displayName.isBlank())) ? name : displayName,
                    location,
                    section.getBoolean(WarpProperty.LOCKED.getKey(), false)));

        } catch (final IllegalArgumentException exception) {
            this.multiWarpApi.getLogger().warning(String.format("Skipping Warp %s: %s", name, exception.getMessage()));
            return Optional.empty();
        }
    }

    @Override
    public @NotNull Collection<Warp> loadAll() {
        return super.getFileConfiguration().getKeys().stream()
                .map(this::load)
                .flatMap(Optional::stream)
                .toList();
    }

    @Override
    public void sanitize() {
        final FileConfiguration configuration = super.getFileConfiguration();
        final List<String> invalidNames = new ArrayList<>();

        for (final String name : configuration.getKeys()) {
            final Optional<Warp> warp = this.load(name);

            if (warp.isEmpty()) {
                invalidNames.add(name);
                continue;
            }
            configuration.set(name, this.serializeWarp(warp.get()));
        }

        if (configuration.getKeys().isEmpty()) {
            return;
        }
        final File file = super.getConfigurationFile();
        final File backupFile = new File(file.getParentFile(), String.format("%s.%d.bak", file.getName(), System.currentTimeMillis()));

        try {
            if (!invalidNames.isEmpty()) {
                Files.copy(file.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                invalidNames.forEach(this::removeEntry);
            }
            this.saveFile();

        } catch (final IOException | RuntimeException exception) {
            this.multiWarpApi.getLogger().log(Level.SEVERE, String.format("Can't sanitize File %s", file.getName()), exception);
            return;
        }

        if (!invalidNames.isEmpty()) {
            this.multiWarpApi.getLogger().warning(String.format("Removed: [%d] invalid Warps from %s (backup: %s)",
                    invalidNames.size(), file.getName(), backupFile.getName()));
        }
    }

    private void applyWithRollback(@NotNull final Map<String, Object> changes) {
        final FileConfiguration configuration = super.getFileConfiguration();
        final Map<String, Object> previousValues = new LinkedHashMap<>();

        for (final String name : changes.keySet()) {
            previousValues.put(name, configuration.get(name));
        }
        try {
            changes.forEach(configuration::set);
            this.saveFile();

        } catch (final RuntimeException exception) {
            previousValues.forEach(configuration::set);
            throw exception;
        }
    }

    private @NotNull Map<String, Object> serializeWarp(@NotNull final Warp warp) {
        final Map<String, Object> section = new LinkedHashMap<>();
        section.put(WarpProperty.DISPLAY_NAME.getKey(), warp.getDisplayName());
        section.put(WarpProperty.LOCATION.getKey(), this.serializeLocation(warp.getLocation()));
        section.put(WarpProperty.LOCKED.getKey(), warp.isLocked());
        return section;
    }

    private @NotNull Map<String, Object> serializeLocation(@NotNull final WarpLocation location) {
        final Map<String, Object> section = new LinkedHashMap<>();
        section.put(DefaultWarpRepository.WORLD_KEY, location.getWorldName());
        section.put(DefaultWarpRepository.X_KEY, location.getX());
        section.put(DefaultWarpRepository.Y_KEY, location.getY());
        section.put(DefaultWarpRepository.Z_KEY, location.getZ());
        section.put(DefaultWarpRepository.YAW_KEY, location.getYaw());
        section.put(DefaultWarpRepository.PITCH_KEY, location.getPitch());
        return section;
    }

    private boolean hasRequiredCoordinates(@NotNull final FileConfiguration locationSection) {
        return (locationSection.get(DefaultWarpRepository.X_KEY) instanceof Number)
                && (locationSection.get(DefaultWarpRepository.Y_KEY) instanceof Number)
                && (locationSection.get(DefaultWarpRepository.Z_KEY) instanceof Number);
    }

    private void removeEntry(@NotNull final String key) {
        if (this.isValidKey(key)) {
            super.getFileConfiguration().set(key, null);
            return;
        }
        super.getFileConfiguration().self.remove(key);
    }

    private boolean isValidKey(@NotNull final String key) {
        return (!key.isEmpty()) && (key.indexOf('.') == -1);
    }

    private void checkKey(@NotNull final String key) {
        if (!this.isValidKey(key)) {
            throw new IllegalArgumentException(String.format("Invalid storage key: %s", key));
        }
    }

}
