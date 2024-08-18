package com.dev7ex.multiwarp.api.warp;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a warp point in the MultiWarp plugin.
 * Provides methods to get and set various properties of the warp.
 *
 * @since 15.08.2024
 */
public interface Warp {

    /**
     * Gets the name of the warp.
     *
     * @return The warp name.
     */
    String getName();

    /**
     * Sets the name of the warp.
     *
     * @param name The new name for the warp. Cannot be null.
     */
    void setName(@NotNull final String name);

    /**
     * Gets the name of the warp creator.
     *
     * @return The creator's name.
     */
    String getCreatorName();

    /**
     * Sets the name of the warp creator.
     *
     * @param creatorName The creator's name. Cannot be null.
     */
    void setCreatorName(@NotNull final String creatorName);

    /**
     * Gets the creation timestamp of the warp.
     *
     * @return The creation timestamp.
     */
    long getCreationTimeStamp();

    /**
     * Sets the creation timestamp of the warp.
     *
     * @param creationTimeStamp The new creation timestamp.
     */
    void setCreationTimeStamp(final long creationTimeStamp);

    /**
     * Checks if the warp is locked.
     *
     * @return {@code true} if the warp is locked, otherwise {@code false}.
     */
    boolean isLocked();

    /**
     * Sets whether the warp is locked.
     *
     * @param locked {@code true} to lock the warp, {@code false} to unlock.
     */
    void setLocked(final boolean locked);

    /**
     * Gets the permission required to use the warp.
     *
     * @return The permission string.
     */
    String getPermission();

    /**
     * Sets the permission required to use the warp.
     *
     * @param permission The permission string.
     */
    void setPermission(final String permission);

    /**
     * Gets the name of the world where the warp is located.
     *
     * @return The world name.
     */
    String getWorldName();

    /**
     * Sets the name of the world where the warp is located.
     *
     * @param worldName The world name. Cannot be null.
     */
    void setWorldName(@NotNull final String worldName);

    /**
     * Gets the X coordinate of the warp location.
     *
     * @return The X coordinate.
     */
    double getX();

    /**
     * Sets the X coordinate of the warp location.
     *
     * @param x The X coordinate.
     */
    void setX(final double x);

    /**
     * Gets the Y coordinate of the warp location.
     *
     * @return The Y coordinate.
     */
    double getY();

    /**
     * Sets the Y coordinate of the warp location.
     *
     * @param y The Y coordinate.
     */
    void setY(final double y);

    /**
     * Gets the Z coordinate of the warp location.
     *
     * @return The Z coordinate.
     */
    double getZ();

    /**
     * Sets the Z coordinate of the warp location.
     *
     * @param z The Z coordinate.
     */
    void setZ(final double z);

    /**
     * Gets the pitch of the warp location.
     *
     * @return The pitch.
     */
    double getPitch();

    /**
     * Sets the pitch of the warp location.
     *
     * @param pitch The pitch.
     */
    void setPitch(final double pitch);

    /**
     * Gets the yaw of the warp location.
     *
     * @return The yaw.
     */
    double getYaw();

    /**
     * Sets the yaw of the warp location.
     *
     * @param yaw The yaw.
     */
    void setYaw(final double yaw);

    /**
     * Updates the value of a specific warp flag.
     *
     * @param flag The flag to update. Cannot be null.
     * @param value The new value for the flag. Cannot be null.
     */
    void updateFlag(@NotNull final WarpFlag flag, @NotNull final String value);

    /**
     * Checks if the user has permission to access the warp.
     *
     * @return {@code true} if the user has permission, otherwise {@code false}.
     */
    boolean hasPermission();

}
