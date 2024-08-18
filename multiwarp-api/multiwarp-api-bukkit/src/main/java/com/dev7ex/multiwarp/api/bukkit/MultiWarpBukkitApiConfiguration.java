package com.dev7ex.multiwarp.api.bukkit;

import com.dev7ex.common.bukkit.plugin.configuration.DefaultPluginConfiguration;
import com.dev7ex.common.io.file.configuration.ConfigurationHolder;
import com.dev7ex.multiwarp.api.MultiWarpApiConfiguration;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract class for the Bukkit-specific configuration of the MultiWarp API.
 * Extends DefaultPluginConfiguration to provide default configuration handling.
 *
 * @since 15.08.2024
 */
public abstract class MultiWarpBukkitApiConfiguration extends DefaultPluginConfiguration implements MultiWarpApiConfiguration {

    /**
     * Constructor for MultiWarpBukkitApiConfiguration.
     *
     * @param configurationHolder The holder for configuration data.
     */
    public MultiWarpBukkitApiConfiguration(@NotNull final ConfigurationHolder configurationHolder) {
        super(configurationHolder);
    }

    /**
     * Enum for defining configuration entries with paths, default values, and removal status.
     */
    @Getter(AccessLevel.PUBLIC)
    public enum Entry {

        PREFIX("prefix", "§8[§bMultiWorld§8]§r", false),

        SETTINGS_MONITORING_SYSTEM_ENABLED("settings.monitoring-system-enabled", true, false),

        SETTINGS_TIME_FORMAT("settings.time-format", "dd.MM.yyyy HH:mm:ss", false);

        private final String path;
        private final Object defaultValue;
        private final boolean removed;

        /**
         * Constructor for configuration entry enum.
         *
         * @param path The path to the configuration entry.
         * @param defaultValue The default value for the configuration entry.
         * @param removed Indicates if the entry has been deprecated.
         */
        Entry(@NotNull final String path, @NotNull final Object defaultValue, final boolean removed) {
            this.path = path;
            this.defaultValue = defaultValue;
            this.removed = removed;
        }
    }

}
