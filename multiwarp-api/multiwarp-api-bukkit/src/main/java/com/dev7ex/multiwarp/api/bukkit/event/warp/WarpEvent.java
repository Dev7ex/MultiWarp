package com.dev7ex.multiwarp.api.bukkit.event.warp;

import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a base class for all events related to warps in the system.
 * This class is abstract and serves as a parent for more specific warp-related events.
 * It provides a reference to the {@link BukkitWarp} object associated with the event.
 *
 * @author Dev7ex
 * @since 15.08.2024
 */
@Getter(AccessLevel.PUBLIC)
public abstract class WarpEvent extends Event {

    private final BukkitWarp warp;

    /**
     * Constructs a new WarpEvent.
     *
     * @param warp The warp associated with this event.
     */
    public WarpEvent(@NotNull final BukkitWarp warp) {
        this.warp = warp;
    }

}
