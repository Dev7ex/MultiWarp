package com.dev7ex.multiwarp.api.event;

import com.dev7ex.multiwarp.api.MultiWarpApi;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
public abstract class MultiWarpListener implements Listener {

    protected final MultiWarpApi multiWarpApi;

    protected MultiWarpListener(@NotNull final MultiWarpApi multiWarpApi) {
        this.multiWarpApi = multiWarpApi;
    }



}
