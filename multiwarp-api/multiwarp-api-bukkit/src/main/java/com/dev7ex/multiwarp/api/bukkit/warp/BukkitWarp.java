package com.dev7ex.multiwarp.api.bukkit.warp;

import com.dev7ex.multiwarp.api.warp.Warp;
import com.dev7ex.multiwarp.api.warp.WarpFlag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a warp point in the Bukkit environment.
 * Implements the Warp interface with Bukkit-specific functionalities.
 *
 * @author Dev7ex
 * @since 15.08.2024
 *
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder(setterPrefix = "set")
public class BukkitWarp implements Warp {

    private String name;
    private String creatorName;
    private long creationTimeStamp;
    private boolean locked;
    private String permission;
    private String worldName;
    private double x;
    private double y;
    private double z;
    private double pitch;
    private double yaw;

    /**
     * Constructs a new BukkitWarp with the specified attributes.
     *
     * @param name The name of the warp.
     * @param creatorName The name of the creator.
     * @param creationTimeStamp The creation timestamp.
     * @param locked Whether the warp is locked.
     * @param permission The permission required to use the warp, or null if no permission is required.
     * @param worldName The name of the world where the warp is located.
     * @param x The X coordinate of the warp location.
     * @param y The Y coordinate of the warp location.
     * @param z The Z coordinate of the warp location.
     * @param pitch The pitch of the warp location.
     * @param yaw The yaw of the warp location.
     */
    public BukkitWarp(@NotNull final String name, @NotNull final String creatorName, final long creationTimeStamp,
                      final boolean locked, @Nullable final String permission,
                      @NotNull final String worldName, double x, double y, double z,
                      double pitch, double yaw) {
        this.name = name;
        this.creatorName = creatorName;
        this.creationTimeStamp = creationTimeStamp;
        this.locked = locked;
        this.permission = permission;
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    /**
     * Converts this warp to a Bukkit Location object.
     *
     * @return The Bukkit Location object representing this warp.
     */
    public Location toLocation() {
        return new Location(Bukkit.getWorld(this.worldName), x, y, z, (float) yaw, (float) pitch);
    }

    /**
     * Updates the value of a specific WarpFlag based on the provided value.
     *
     * @param flag The flag to update.
     * @param value The new value for the flag.
     */
    @Override
    public void updateFlag(@NotNull final WarpFlag flag, @NotNull final String value) {
        try {
            switch (flag) {
                case LOCKED:
                    this.locked = Boolean.parseBoolean(value);
                    break;

                case PERMISSION:
                    this.permission = value;
                    break;

                case WORLD_NAME:
                    this.worldName = value;
                    break;

                case X:
                    this.x = Double.parseDouble(value);
                    break;

                case Y:
                    this.y = Double.parseDouble(value);
                    break;

                case Z:
                    this.z = Double.parseDouble(value);
                    break;

                case PITCH:
                    this.pitch = Double.parseDouble(value);
                    break;

                case YAW:
                    this.yaw = Double.parseDouble(value);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown WarpFlag: " + flag);
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format for coordinates or angles
            throw new IllegalArgumentException("Invalid number format for value: " + value, e);
        }
    }

    /**
     * Checks if this warp has a non-null and non-blank permission.
     *
     * @return True if the warp has a valid permission, false otherwise.
     */
    @Override
    public boolean hasPermission() {
        return (this.permission != null) && (!this.permission.isBlank());
    }

}
