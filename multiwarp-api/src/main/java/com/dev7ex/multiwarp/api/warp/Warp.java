package com.dev7ex.multiwarp.api.warp;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
public interface Warp extends Comparable<Warp>, Cloneable {

    @NotNull
    String getName();

    void setName(@NotNull final String name);

    @NotNull
    String getDisplayName();

    void setDisplayName(@NotNull final String displayName);

    @Override
    default int compareTo(@NotNull final Warp warp) {
        return this.getName().compareTo(warp.getName());
    }

}
