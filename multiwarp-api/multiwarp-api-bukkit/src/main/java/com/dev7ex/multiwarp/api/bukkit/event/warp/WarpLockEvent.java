package com.dev7ex.multiwarp.api.bukkit.event.warp;

import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is triggered when a warp is locked.
 * It provides access to the {@link BukkitWarp} that is being locked, allowing for custom handling during the locking process.
 *
 * @author Dev7ex
 * @since 16.08.2024
 */
public class WarpLockEvent extends WarpEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Constructs a new WarpLockEvent.
     *
     * @param warp The warp that is being locked.
     */
    public WarpLockEvent(@NotNull final BukkitWarp warp) {
        super(warp);
    }

    /**
     * Returns the HandlerList for this event.
     *
     * @return The handler list.
     */
    public static HandlerList getHandlerList() {
        return WarpLockEvent.HANDLERS;
    }

    /**
     * Returns the handlers for this event.
     *
     * @return The handler list.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return WarpLockEvent.HANDLERS;
    }

}
