package com.dev7ex.multiwarp.api.event.warp;

import com.dev7ex.multiwarp.api.warp.Warp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public abstract class WarpEvent extends Event {

    protected Warp warp;

    protected WarpEvent(@NotNull final Warp warp) {
        this.warp = warp;
    }

}
