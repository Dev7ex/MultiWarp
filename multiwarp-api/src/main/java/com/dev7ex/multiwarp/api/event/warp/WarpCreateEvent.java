package com.dev7ex.multiwarp.api.event.warp;

import com.dev7ex.multiwarp.api.warp.Warp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class WarpCreateEvent extends WarpEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;
    private boolean notifyOnCancel = true;

    public WarpCreateEvent(@NotNull final Warp warp) {
        super(warp);
    }

    public static HandlerList getHandlerList() {
        return WarpCreateEvent.HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return WarpCreateEvent.HANDLERS;
    }

}
