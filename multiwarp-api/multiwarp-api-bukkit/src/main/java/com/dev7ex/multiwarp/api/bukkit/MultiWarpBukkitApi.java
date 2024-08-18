package com.dev7ex.multiwarp.api.bukkit;

import com.dev7ex.multiwarp.api.MultiWarpApi;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarpConfiguration;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarpProvider;

/**
 * Interface for the Bukkit-specific implementation of the MultiWarp API.
 * Extends the general MultiWarpApi to provide Bukkit-specific configurations and providers.
 *
 * @since 15.08.2024
 */
public interface MultiWarpBukkitApi extends MultiWarpApi {

    /**
     * Retrieves the Bukkit-specific API configuration.
     *
     * @return The Bukkit API configuration.
     */
    @Override
    MultiWarpBukkitApiConfiguration getConfiguration();

    /**
     * Retrieves the Bukkit-specific warp configuration.
     *
     * @return The Bukkit warp configuration.
     */
    @Override
    BukkitWarpConfiguration getWarpConfiguration();

    /**
     * Retrieves the Bukkit-specific warp provider.
     *
     * @return The Bukkit warp provider.
     */
    @Override
    BukkitWarpProvider getWarpProvider();

}
