package com.dev7ex.multiwarp.api.warp;

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

    void deleteWarp(@NotNull final String name);

    @NotNull
    Warp renameWarp(@NotNull final String name, @NotNull final String newName);

    void updateWarp(@NotNull final String name, @NotNull final WarpProperty property, @NotNull final Object value);

    @NotNull
    CompletableFuture<Boolean> teleport(@NotNull final Player player, @NotNull final Warp warp);

}
