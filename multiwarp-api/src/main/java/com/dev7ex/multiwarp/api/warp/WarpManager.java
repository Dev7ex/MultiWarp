package com.dev7ex.multiwarp.api.warp;

import net.kyori.adventure.audience.Audience;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * @author MGG
 * @since 15.11.2026
 */
public interface WarpManager {

    @NotNull
    Warp createWarp(@NotNull final String name, @NotNull final WarpLocation location);

    Warp create(@NotNull final Audience audience, @NotNull final String name, @NotNull final WarpLocation location);

    Warp create(@NotNull final Audience audience, @NotNull final String name, @NotNull final Player player);

    Warp create(@NotNull final Audience audience, @NotNull final String name, @NotNull final Location location);

    void deleteWarp(@NotNull final String name);

    @NotNull
    Warp renameWarp(@NotNull final String name, @NotNull final String newName);

    void updateWarp(@NotNull final String name, @NotNull final WarpProperty property, @NotNull final Object value);

    @NotNull
    CompletableFuture<Boolean> teleport(@NotNull final Player player, @NotNull final Warp warp);

}
