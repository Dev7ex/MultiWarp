package com.dev7ex.multiwarp.api.warp;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
@Getter(AccessLevel.PUBLIC)
public enum WarpProperty {

    NAME("name", String.class),
    DISPLAY_NAME("display-name", String.class),
    LOCATION("location", WarpLocation.class),
    LOCKED("locked", Boolean.class);

    private final String key;
    private final Class<?> type;

    WarpProperty(@NotNull final String key, @NotNull final Class<?> type) {
        this.key = key;
        this.type = type;
    }

    @NotNull
    public static Optional<WarpProperty> fromKey(@NotNull final String key) {
        return Arrays.stream(WarpProperty.values())
                .filter(property -> property.getKey().equalsIgnoreCase(key))
                .findFirst();
    }

}
