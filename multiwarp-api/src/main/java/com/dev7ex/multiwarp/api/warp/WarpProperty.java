package com.dev7ex.multiwarp.api.warp;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
public enum WarpProperty {

    NAME("name", String.class);

    private final String key;
    private final Class<?> type;

    WarpProperty(@NotNull final String key, @NotNull final Class<?> type) {
        this.key = key;
        this.type = type;
    }

}
