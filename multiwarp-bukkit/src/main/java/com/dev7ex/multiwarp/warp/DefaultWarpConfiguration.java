package com.dev7ex.multiwarp.warp;

import com.dev7ex.common.io.file.configuration.Configuration;
import com.dev7ex.common.io.file.configuration.ConfigurationHolder;
import com.dev7ex.common.io.file.configuration.ConfigurationProperties;
import com.dev7ex.common.io.file.configuration.JsonConfiguration;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarpConfiguration;
import com.dev7ex.multiwarp.api.warp.WarpProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@ConfigurationProperties(fileName = "warp.json", provider = JsonConfiguration.class)
public class DefaultWarpConfiguration extends Configuration implements BukkitWarpConfiguration {

    public DefaultWarpConfiguration(@NotNull final ConfigurationHolder configurationHolder) {
        super(configurationHolder);
    }

    @Override
    public void add(@NotNull final BukkitWarp warp) {
        super.getFileConfiguration().set(warp.getName() + "." + WarpProperty.CREATOR_NAME.getStoragePath(), warp.getCreatorName());
        super.getFileConfiguration().set(warp.getName() + "." + WarpProperty.CREATION_TIME_STAMP.getStoragePath(), warp.getCreationTimeStamp());
        super.getFileConfiguration().set(warp.getName() + "." + WarpProperty.LOCKED.getStoragePath(), warp.isLocked());
        super.getFileConfiguration().set(warp.getName() + "." + WarpProperty.PERMISSION.getStoragePath(), warp.getPermission());
        super.getFileConfiguration().set(warp.getName() + "." + WarpProperty.WORLD_NAME.getStoragePath(), warp.getWorldName());
        super.getFileConfiguration().set(warp.getName() + "." + WarpProperty.X.getStoragePath(), warp.getX());
        super.getFileConfiguration().set(warp.getName() + "." + WarpProperty.Y.getStoragePath(), warp.getY());
        super.getFileConfiguration().set(warp.getName() + "." + WarpProperty.Z.getStoragePath(), warp.getZ());
        super.getFileConfiguration().set(warp.getName() + "." + WarpProperty.PITCH.getStoragePath(), warp.getPitch());
        super.getFileConfiguration().set(warp.getName() + "." + WarpProperty.YAW.getStoragePath(), warp.getYaw());
        super.saveFile();
    }

    @Override
    public void remove(@NotNull final BukkitWarp warp) {
        super.getFileConfiguration().set(warp.getName(), null);
        super.saveFile();
    }

    @Override
    public void remove(@NotNull final String name) {
        super.getFileConfiguration().set(name, null);
        super.saveFile();
    }

    @Override
    public boolean contains(@NotNull final BukkitWarp warp) {
        return super.getFileConfiguration().contains(warp.getName());
    }

    @Override
    public boolean contains(@NotNull final String name) {
        return super.getFileConfiguration().contains(name);
    }

    @Override
    public void write(@NotNull final BukkitWarp warp, @NotNull final WarpProperty property, @NotNull final Object data) {
        switch (property) {
            case NAME:
            case CREATOR_NAME:
            case PERMISSION:
            case WORLD_NAME:
                super.getFileConfiguration().set(warp.getName() + "." + property.getStoragePath(), String.valueOf(data));
                break;

            case CREATION_TIME_STAMP:
                super.getFileConfiguration().set(warp.getName() + "." + property.getStoragePath(), Long.parseLong(String.valueOf(data)));
                break;

            case LOCKED:
                super.getFileConfiguration().set(warp.getName() + "." + property.getStoragePath(), Boolean.parseBoolean(String.valueOf(data)));
                break;

            case X:
            case Y:
            case Z:
            case PITCH:
            case YAW:
                super.getFileConfiguration().set(warp.getName() + "." + property.getStoragePath(), Double.parseDouble(String.valueOf(data)));
                break;
        }
        super.saveFile();
    }

    @Override
    public BukkitWarp getWarp(@NotNull final String name) {
        return BukkitWarp.builder()
                .setName(name)
                .setCreatorName(super.getFileConfiguration().getString(name + "." + WarpProperty.CREATOR_NAME.getStoragePath()))
                .setCreationTimeStamp(super.getFileConfiguration().getLong(name + "." + WarpProperty.CREATION_TIME_STAMP.getStoragePath()))
                .setLocked(super.getFileConfiguration().getBoolean(name + "." + WarpProperty.LOCKED.getStoragePath()))
                .setPermission(super.getFileConfiguration().getString(name + "." + WarpProperty.PERMISSION.getStoragePath()))
                .setWorldName(super.getFileConfiguration().getString(name + "." + WarpProperty.WORLD_NAME.getStoragePath()))
                .setX(super.getFileConfiguration().getDouble(name + "." + WarpProperty.X.getStoragePath()))
                .setY(super.getFileConfiguration().getDouble(name + "." + WarpProperty.Y.getStoragePath()))
                .setZ(super.getFileConfiguration().getDouble(name + "." + WarpProperty.Z.getStoragePath()))
                .setPitch(super.getFileConfiguration().getDouble(name + "." + WarpProperty.PITCH.getStoragePath()))
                .setYaw(super.getFileConfiguration().getDouble(name + "." + WarpProperty.YAW.getStoragePath()))
                .build();
    }

    @Override
    public @NotNull Map<String, BukkitWarp> getWarps() {
        return super.getFileConfiguration().getKeys()
                .stream()
                .collect(Collectors.toMap(entry -> entry, this::getWarp));
    }

}
