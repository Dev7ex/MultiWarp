package com.dev7ex.multiwarp.api.event;

import com.dev7ex.multiwarp.api.MultiWarpApi;
import com.dev7ex.multiwarp.api.MultiWarpApiConfiguration;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import com.dev7ex.multiwarp.api.module.WarpModuleManager;
import com.dev7ex.multiwarp.api.warp.WarpFactory;
import com.dev7ex.multiwarp.api.warp.WarpManager;
import com.dev7ex.multiwarp.api.warp.WarpRegistry;
import com.dev7ex.multiwarp.api.warp.WarpRepository;
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

    protected MultiWarpApiConfiguration getConfiguration() {
        return this.multiWarpApi.getConfiguration();
    }

    protected WarpLocalizationRegistry getLocalizationRegistry() {
        return this.multiWarpApi.getLocalizationRegistry();
    }

    protected WarpModuleManager getModuleManager() {
        return this.multiWarpApi.getModuleManager();
    }

    protected WarpFactory getWarpFactory() {
        return this.multiWarpApi.getWarpFactory();
    }

    protected WarpManager getWarpManager() {
        return this.multiWarpApi.getWarpManager();
    }

    protected WarpRegistry getWarpRegistry() {
        return this.multiWarpApi.getWarpRegistry();
    }

    protected WarpRepository getWarpRepository() {
        return this.multiWarpApi.getWarpRepository();
    }

}
