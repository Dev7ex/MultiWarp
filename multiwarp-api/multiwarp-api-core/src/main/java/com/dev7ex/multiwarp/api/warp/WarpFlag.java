package com.dev7ex.multiwarp.api.warp;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Enum representing different flags used in warp configurations.
 * Each flag has an associated storage path, allowed values, and data type.
 *
 * @since 16.08.2024
 */
@Getter(AccessLevel.PUBLIC)
public enum WarpFlag {

    LOCKED("locked", List.of("true", "false"), Boolean.class),
    PERMISSION("permission", Collections.emptyList(), String.class),
    WORLD_NAME("world-name", Collections.emptyList(), String.class),
    X("x", Collections.emptyList(), Double.class),
    Y("y", Collections.emptyList(), Double.class),
    Z("z", Collections.emptyList(), Double.class),
    PITCH("pitch", Collections.emptyList(), Double.class),
    YAW("yaw", Collections.emptyList(), Double.class);

    private final String storagePath;
    private final List<String> values;
    private final Class<?> dataType;

    /**
     * Constructor for WarpFlag.
     *
     * @param storagePath the path used to store the flag's value
     * @param values      allowed values for the flag
     * @param dataType    the type of data associated with the flag
     */
    WarpFlag(@NotNull final String storagePath, @NotNull final List<String> values, @NotNull final Class<?> dataType) {
        this.storagePath = storagePath;
        this.values = values;
        this.dataType = dataType;
    }

    /**
     * Converts all WarpFlag names to a list of strings.
     *
     * @return a list of string names of all WarpFlags
     */
    @NotNull
    public static List<String> toStringList() {
        return Arrays.stream(WarpFlag.values())
                .map(Enum::name)
                .toList();
    }

    /**
     * Finds a WarpFlag by its string name.
     *
     * @param name the name of the WarpFlag to find
     * @return an Optional containing the WarpFlag if found, otherwise empty
     */
    @NotNull
    public static Optional<WarpFlag> fromString(@NotNull final String name) {
        return Arrays.stream(WarpFlag.values())
                .filter(flag -> flag.name().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Converts a WarpFlag to its corresponding WarpProperty.
     *
     * @param flag the WarpFlag to convert
     * @return the corresponding WarpProperty
     * @throws IllegalArgumentException if the WarpFlag does not map to any WarpProperty
     */
    @NotNull
    public static WarpProperty toProperty(@NotNull final WarpFlag flag) {
        switch (flag) {
            case LOCKED:
                return WarpProperty.LOCKED;

            case PERMISSION:
                return WarpProperty.PERMISSION;

            case WORLD_NAME:
                return WarpProperty.WORLD_NAME;

            case X:
                return WarpProperty.X;

            case Y:
                return WarpProperty.Y;

            case Z:
                return WarpProperty.Z;

            case PITCH:
                return WarpProperty.PITCH;

            case YAW:
                return WarpProperty.YAW;

            default:
                throw new IllegalArgumentException("Unknown WarpFlag: " + flag);
        }
    }

}
