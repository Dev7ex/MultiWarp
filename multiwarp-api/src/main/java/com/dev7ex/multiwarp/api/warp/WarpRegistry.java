package com.dev7ex.multiwarp.api.warp;

import com.dev7ex.multiwarp.api.module.WarpModule;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
public interface WarpRegistry extends WarpModule {

    void register(@NotNull final Warp warp);

    void unregister(@NotNull final String name);

    boolean isRegistered(@NotNull final String name);

    @NotNull
    Optional<Warp> find(@NotNull final String name);

    @NotNull
    Collection<Warp> getWarps();

    @NotNull
    List<Warp> getSortedWarps();

}
