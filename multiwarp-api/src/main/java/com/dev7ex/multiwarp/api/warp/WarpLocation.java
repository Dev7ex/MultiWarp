package com.dev7ex.multiwarp.api.warp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * @author MGG
 * @since 15.11.2026
 */
@Getter(AccessLevel.PUBLIC)
@ToString
@EqualsAndHashCode
public class WarpLocation {

    @NotNull
    private final String worldName;

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    @Builder(toBuilder = true, setterPrefix = "set")
    private WarpLocation(@NotNull final String worldName, final double x, final double y, final double z, final float yaw, final float pitch) {
        if ((worldName == null) || (worldName.isBlank())) {
            throw new IllegalArgumentException(String.format("Invalid world name: %s", worldName));
        }

        if ((!Double.isFinite(x)) || (!Double.isFinite(y)) || (!Double.isFinite(z))
                || (!Float.isFinite(yaw)) || (!Float.isFinite(pitch))) {
            throw new IllegalArgumentException(String.format("Location values must be finite: %s, %s, %s, %s, %s", x, y, z, yaw, pitch));
        }

        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @NotNull
    public static WarpLocation fromLocation(@NotNull final Location location) {
        final World world = location.getWorld();

        if (world == null) {
            throw new IllegalArgumentException(String.format("Location has no world: %s", location));
        }
        return WarpLocation.builder()
                .setWorldName(world.getName())
                .setX(location.getX())
                .setY(location.getY())
                .setZ(location.getZ())
                .setYaw(location.getYaw())
                .setPitch(location.getPitch())
                .build();
    }

    @NotNull
    public Optional<Location> toLocation() {
        final World world = Bukkit.getWorld(this.worldName);

        if (world == null) {
            return Optional.empty();
        }
        return Optional.of(new Location(world, this.x, this.y, this.z, this.yaw, this.pitch));
    }

}
