package com.dev7ex.multiwarp.api.bukkit.event.warp;

import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import com.dev7ex.multiwarp.api.warp.WarpFlag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is triggered when a warp's flag is changed.
 * It provides access to the specific {@link WarpFlag} that is being modified and the new value being set.
 * The event is cancellable, allowing you to prevent the flag change if necessary.
 *
 * @author Dev7ex
 * @since 16.08.2024
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class WarpFlagChangeEvent extends WarpEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;
    private WarpFlag flag;
    private Object value;

    /**
     * Constructs a new WarpFlagChangeEvent.
     *
     * @param warp The warp whose flag is being changed.
     * @param flag The warp flag that is being changed.
     * @param value The new value for the flag.
     */
    public WarpFlagChangeEvent(@NotNull final BukkitWarp warp, @NotNull final WarpFlag flag, @NotNull final Object value) {
        super(warp);
        this.flag = flag;
        this.value = value;
    }

    /**
     * Returns the HandlerList for this event.
     *
     * @return The handler list.
     */
    public static HandlerList getHandlerList() {
        return WarpFlagChangeEvent.HANDLERS;
    }

    /**
     * Returns the handlers for this event.
     *
     * @return The handler list.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return WarpFlagChangeEvent.HANDLERS;
    }

}
