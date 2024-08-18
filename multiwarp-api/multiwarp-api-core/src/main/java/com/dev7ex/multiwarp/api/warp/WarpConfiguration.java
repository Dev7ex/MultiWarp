package com.dev7ex.multiwarp.api.warp;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Defines the configuration for managing warps.
 *
 * @param <T> the type of Warp this configuration handles
 * @since 15.08.2024
 */
public interface WarpConfiguration<T extends Warp> {

    /**
     * Adds a new warp to the configuration.
     *
     * @param warp the warp to add; must not be null
     */
    void add(@NotNull T warp);

    /**
     * Removes a warp from the configuration.
     *
     * @param warp the warp to remove; must not be null
     */
    void remove(@NotNull T warp);

    /**
     * Removes a warp from the configuration by its name.
     *
     * @param name the name of the warp to remove; must not be null
     */
    void remove(@NotNull String name);

    /**
     * Checks if a warp exists in the configuration.
     *
     * @param warp the warp to check; must not be null
     * @return true if the warp is present, false otherwise
     */
    boolean contains(@NotNull T warp);

    /**
     * Checks if a warp with the given name exists in the configuration.
     *
     * @param name the name of the warp to check; must not be null
     * @return true if a warp with the given name is present, false otherwise
     */
    boolean contains(@NotNull String name);

    /**
     * Writes a property value to a warp.
     *
     * @param warp     the warp to update; must not be null
     * @param property the property to update; must not be null
     * @param data     the new value for the property; must not be null
     */
    void write(@NotNull T warp, @NotNull WarpProperty property, @NotNull Object data);

    /**
     * Retrieves a warp by its name.
     *
     * @param name the name of the warp to retrieve; must not be null
     * @return the warp associated with the given name
     */
    T getWarp(@NotNull String name);

    /**
     * Retrieves all warps in the configuration.
     *
     * @return a map of warp names to their corresponding warp objects
     */
    @NotNull Map<String, T> getWarps();

}
