package com.dev7ex.multiwarp.api.warp;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Enum for representing properties of a Warp object.
 * Defines all possible properties and provides methods for converting between names and storage paths.
 *
 * @since 15.08.2024
 */
@Getter(AccessLevel.PUBLIC)
public enum WarpProperty {

    NAME("name"),
    CREATOR_NAME("creator-name"),
    CREATION_TIME_STAMP("creation-timestamp"),
    LOCKED("locked"),
    PERMISSION("permission"),
    WORLD_NAME("world-name"),
    X("x"),
    Y("y"),
    Z("z"),
    PITCH("pitch"),
    YAW("yaw");

    private final String storagePath;

    WarpProperty(@NotNull final String storagePath) {
        this.storagePath = storagePath;
    }

    /**
     * Returns a list of the names of all WarpProperty values.
     *
     * @return A list of strings containing the names of the enum values.
     */
    public static List<String> toStringList() {
        return Arrays.stream(WarpProperty.values())
                .map(Enum::name)
                .toList();
    }

    /**
     * Finds a WarpProperty by its name.
     *
     * @param name The name of the WarpProperty.
     * @return An Optional containing the found WarpProperty, or empty if no match is found.
     */
    public static Optional<WarpProperty> fromString(final String name) {
        return Arrays.stream(WarpProperty.values())
                .filter(property -> property.name().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Finds a WarpProperty by its storage path.
     *
     * @param storagePath The storage path of the WarpProperty.
     * @return An Optional containing the found WarpProperty, or empty if no match is found.
     */
    public static Optional<WarpProperty> fromStoragePath(@NotNull final String storagePath) {
        return Arrays.stream(WarpProperty.values())
                .filter(property -> property.getStoragePath().equalsIgnoreCase(storagePath))
                .findFirst();
    }

}
