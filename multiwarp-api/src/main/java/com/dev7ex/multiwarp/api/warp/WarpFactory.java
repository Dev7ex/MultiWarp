package com.dev7ex.multiwarp.api.warp;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
public interface WarpFactory {

    @NotNull
    Warp createWarp(@NotNull final String name, @NotNull final WarpLocation location);

    @NotNull
    Warp createWarp(@NotNull final String name, @NotNull final String displayName, @NotNull final WarpLocation location, final boolean locked);

}
