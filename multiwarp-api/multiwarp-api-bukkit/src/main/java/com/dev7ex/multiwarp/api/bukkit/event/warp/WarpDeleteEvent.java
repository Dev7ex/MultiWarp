package com.dev7ex.multiwarp.api.bukkit.event.warp;

import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is triggered when an existing warp is deleted from the system.
 * It provides access to the warp that is being deleted, allowing for custom handling during the deletion process.
 *
 * @author Dev7ex
 * @since 16.08.2024
 */
public class WarpDeleteEvent extends WarpEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Constructs a new WarpDeleteEvent.
     *
     * @param warp The warp that is being deleted.
     */
    public WarpDeleteEvent(@NotNull final BukkitWarp warp) {
        super(warp);
    }

    /**
     * Returns the HandlerList for this event.
     *
     * @return The handler list.
     */
    public static HandlerList getHandlerList() {
        return WarpDeleteEvent.HANDLERS;
    }

    /**
     * Returns the handlers for this event.
     *
     * @return The handler list.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return WarpDeleteEvent.HANDLERS;
    }

}
