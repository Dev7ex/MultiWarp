package com.dev7ex.multiwarp.api;

import com.dev7ex.multiwarp.api.translation.TranslationProvider;
import com.dev7ex.multiwarp.api.warp.WarpConfiguration;
import com.dev7ex.multiwarp.api.warp.WarpProvider;

/**
 * The {@code MultiWarpApi} interface provides access to the core components
 * of the MultiWarp plugin, including configuration, warp management, and translation.
 *
 * @since 15.08.2024
 */
public interface MultiWarpApi {

    /**
     * Gets the plugin's configuration settings.
     *
     * @return The {@link MultiWarpApiConfiguration} instance.
     */
    MultiWarpApiConfiguration getConfiguration();

    /**
     * Gets the configuration for warps.
     *
     * @return The {@link WarpConfiguration} instance.
     */
    WarpConfiguration<?> getWarpConfiguration();

    /**
     * Gets the provider for managing warps.
     *
     * @return The {@link WarpProvider} instance.
     */
    WarpProvider<?, ?> getWarpProvider();

    /**
     * Gets the translation provider for localizing messages.
     *
     * @return The {@link TranslationProvider} instance.
     */
    TranslationProvider<?> getTranslationProvider();

}
