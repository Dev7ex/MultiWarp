package com.dev7ex.multiwarp.warp;

import com.dev7ex.multiwarp.api.MultiWarpApi;
import com.dev7ex.multiwarp.api.event.warp.WarpCreateEvent;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationEntry;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import com.dev7ex.multiwarp.api.warp.*;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * @author MGG
 * @since 15.11.2026
 */
public class DefaultWarpManager implements WarpManager {

    private final MultiWarpApi multiWarpApi;
    private final WarpLocalizationRegistry localizationRegistry;

    public DefaultWarpManager(@NotNull final MultiWarpApi multiWarpApi) {
        this.multiWarpApi = multiWarpApi;
        this.localizationRegistry = multiWarpApi.getLocalizationRegistry();
    }

    @Override
    public synchronized @NotNull Warp createWarp(@NotNull final String name, @NotNull final WarpLocation location) {
        this.checkAbsent(name);

        final Warp warp = this.multiWarpApi.getWarpFactory().createWarp(name, location);

        this.multiWarpApi.getWarpRepository().create(warp);
        this.multiWarpApi.getWarpRegistry().register(warp);
        return warp;
    }

    @Override
    public synchronized Warp create(@NotNull final Audience audience, @NotNull final String name, @NotNull final WarpLocation location) {
        this.checkAbsent(name);

        final Warp warp = this.multiWarpApi.getWarpFactory().createWarp(name, location);
        final WarpCreateEvent event = new WarpCreateEvent(warp);

        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            if (event.isNotifyOnCancel()) {
                audience.sendMessage(this.localizationRegistry.resolveComponent(audience, WarpLocalizationEntry.COMMAND_WARP_CREATE_CANCELLED)
                        .replaceText(builder -> builder.match("%warp_colored_name%").replacement(warp.getColoredName())));
            }
            return null;
        }

        this.multiWarpApi.getWarpRepository().create(event.getWarp());
        this.multiWarpApi.getWarpRegistry().register(event.getWarp());
        return event.getWarp();
    }

    @Override
    public synchronized Warp create(@NotNull final Audience audience, @NotNull final String name, @NotNull final Player player) {
        return this.create(audience, name, WarpLocation.of(player.getLocation()));
    }

    @Override
    public synchronized Warp create(@NotNull final Audience audience, @NotNull final String name, @NotNull final Location location) {
        return this.create(audience, name, WarpLocation.of(location));
    }

    @Override
    public synchronized void deleteWarp(@NotNull final String name) {
        final Warp warp = this.getExistingWarp(name);

        this.multiWarpApi.getWarpRepository().delete(warp.getName());
        this.multiWarpApi.getWarpRegistry().unregister(warp.getName());
    }

    @Override
    public synchronized @NotNull Warp renameWarp(@NotNull final String name, @NotNull final String newName) {
        final Warp warp = this.getExistingWarp(name);

        if (warp.getName().equals(newName)) {
            return warp;
        }

        if (!warp.getName().equalsIgnoreCase(newName)) {
            this.checkAbsent(newName);
        }
        final String displayName = warp.getDisplayName().equals(warp.getName()) ? newName : warp.getDisplayName();
        final Warp renamedWarp = this.multiWarpApi.getWarpFactory().createWarp(newName, displayName, warp.getLocation(), warp.isLocked());

        this.multiWarpApi.getWarpRepository().rename(warp.getName(), renamedWarp);
        this.multiWarpApi.getWarpRegistry().unregister(warp.getName());
        this.multiWarpApi.getWarpRegistry().register(renamedWarp);
        return renamedWarp;
    }

    @Override
    public synchronized void updateWarp(@NotNull final String name, @NotNull final WarpProperty property, @NotNull final Object value) {
        if (!property.getType().isInstance(value)) {
            throw new IllegalArgumentException(String.format("Invalid value type for property %s: expected %s",
                    property, property.getType().getSimpleName()));
        }

        if (property == WarpProperty.NAME) {
            this.renameWarp(name, (String) value);
            return;
        }
        final Warp warp = this.getExistingWarp(name);
        final WarpFactory warpFactory = this.multiWarpApi.getWarpFactory();

        final Warp updatedWarp = switch (property) {
            case DISPLAY_NAME -> warpFactory.createWarp(warp.getName(), (String) value, warp.getLocation(), warp.isLocked());
            case LOCATION -> warpFactory.createWarp(warp.getName(), warp.getDisplayName(), (WarpLocation) value, warp.isLocked());
            case LOCKED -> warpFactory.createWarp(warp.getName(), warp.getDisplayName(), warp.getLocation(), (Boolean) value);
            default -> throw new IllegalArgumentException(String.format("Unsupported property: %s", property));
        };
        this.multiWarpApi.getWarpRepository().update(updatedWarp);
        this.multiWarpApi.getWarpRegistry().register(updatedWarp);
    }

    @Override
    public @NotNull CompletableFuture<Boolean> teleport(@NotNull final Player player, @NotNull final Warp warp) {
        return warp.getLocation().toLocation()
                .map(location -> player.teleportAsync(location, PlayerTeleportEvent.TeleportCause.PLUGIN))
                .orElseGet(() -> CompletableFuture.completedFuture(false));
    }

    private @NotNull Warp getExistingWarp(@NotNull final String name) {
        return this.multiWarpApi.getWarpRegistry().find(name).orElseThrow(() ->
                new IllegalArgumentException(String.format("Warp does not exist: %s", name)));
    }

    private void checkAbsent(@NotNull final String name) {
        if (this.multiWarpApi.getWarpRegistry().isRegistered(name)) {
            throw new IllegalArgumentException(String.format("Warp already exists: %s", name));
        }
    }

}
