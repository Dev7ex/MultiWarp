package com.dev7ex.multiwarp.api.warp;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

/**
 * Interface for managing warp objects.
 * Provides methods to register, unregister, check, and retrieve warps, as well as teleport entities to warps.
 *
 * @param <T> The type of the Warp object.
 * @param <E> The type of the entity that can be teleported to a Warp.
 * @since 15.08.2024
 */
public interface WarpProvider<T extends Warp, E> {

    /**
     * Registers a new Warp object.
     *
     * @param warp The Warp object to register.
     */
    void register(@NotNull final T warp);

    /**
     * Unregisters a Warp object.
     *
     * @param warp The Warp object to unregister.
     */
    void unregister(@NotNull final T warp);

    /**
     * Unregisters a Warp object by its name.
     *
     * @param name The name of the Warp object to unregister.
     */
    void unregister(@NotNull final String name);

    /**
     * Checks if a Warp object is registered.
     *
     * @param warp The Warp object to check.
     * @return True if the Warp object is registered, false otherwise.
     */
    boolean isRegistered(@NotNull final T warp);

    /**
     * Checks if a Warp object with the specified name is registered.
     *
     * @param name The name of the Warp object to check.
     * @return True if the Warp object with the specified name is registered, false otherwise.
     */
    boolean isRegistered(@NotNull final String name);

    /**
     * Teleports an entity to a specified Warp object.
     *
     * @param entity The entity to teleport.
     * @param warp The Warp object to teleport the entity to.
     */
    void teleport(@NotNull final E entity, @NotNull final T warp);

    /**
     * Retrieves a Warp object by its name.
     *
     * @param name The name of the Warp object.
     * @return An Optional containing the Warp object if found, or empty if not found.
     */
    Optional<T> getWarp(@NotNull final String name);

    /**
     * Returns a map of all registered Warp objects, with names as keys.
     *
     * @return A map where the key is the name of the Warp and the value is the Warp object.
     */
    Map<String, T> getWarps();

}
