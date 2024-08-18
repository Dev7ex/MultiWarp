package com.dev7ex.multiwarp.api.bukkit.event.warp;

import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is triggered when a new warp is created in the system.
 * It provides access to the newly created warp and allows for custom handling during warp creation.
 *
 * @author Dev7ex
 * @since 15.08.2024
 */
public class WarpCreateEvent extends WarpEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Constructs a new WarpCreateEvent.
     *
     * @param warp The warp that is being created.
     */
    public WarpCreateEvent(@NotNull final BukkitWarp warp) {
        super(warp);
    }

    /**
     * Returns the HandlerList for this event.
     *
     * @return The handler list.
     */
    public static HandlerList getHandlerList() {
        return WarpCreateEvent.HANDLERS;
    }

    /**
     * Returns the handlers for this event.
     *
     * @return The handler list.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return WarpCreateEvent.HANDLERS;
    }

}
