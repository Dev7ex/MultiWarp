package com.dev7ex.multiwarp.api;

import java.text.SimpleDateFormat;

/**
 * The {@code MultiWarpApiConfiguration} interface defines key configuration
 * settings for the MultiWarp plugin, such as message prefixes, time formats,
 * and the status of the monitoring system.
 *
 * @since 15.08.2024
 */
public interface MultiWarpApiConfiguration {

    /**
     * Gets the prefix used in plugin messages.
     *
     * @return The message prefix.
     */
    String getPrefix();

    /**
     * Gets the time format used by the plugin.
     *
     * @return The time format as a {@code SimpleDateFormat}.
     */
    SimpleDateFormat getTimeFormat();

    /**
     * Checks if the monitoring system is enabled.
     *
     * @return {@code true} if monitoring is enabled, otherwise {@code false}.
     */
    boolean isMonitoringSystemEnabled();
}
