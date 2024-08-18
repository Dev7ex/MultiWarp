package com.dev7ex.multiwarp.api.bukkit.event;

import com.dev7ex.multiwarp.api.bukkit.MultiWarpBukkitApi;
import com.dev7ex.multiwarp.api.bukkit.MultiWarpBukkitApiConfiguration;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarpConfiguration;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarpProvider;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
public abstract class MultiWarpListener implements Listener {

    private final MultiWarpBukkitApi multiWarpApi;

    public MultiWarpListener(@NotNull final MultiWarpBukkitApi multiWarpApi) {
        this.multiWarpApi = multiWarpApi;
    }

    public MultiWarpBukkitApiConfiguration getConfiguration() {
        return this.multiWarpApi.getConfiguration();
    }

    public BukkitWarpConfiguration getWarpConfiguration() {
        return this.multiWarpApi.getWarpConfiguration();
    }

    public BukkitWarpProvider getWarpProvider() {
        return this.multiWarpApi.getWarpProvider();
    }

}
