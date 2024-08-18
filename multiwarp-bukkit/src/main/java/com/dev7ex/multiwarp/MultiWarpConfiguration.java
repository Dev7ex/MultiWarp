package com.dev7ex.multiwarp;

import com.dev7ex.common.io.file.configuration.ConfigurationHolder;
import com.dev7ex.common.io.file.configuration.ConfigurationProperties;
import com.dev7ex.common.io.file.configuration.YamlConfiguration;
import com.dev7ex.multiwarp.api.bukkit.MultiWarpBukkitApiConfiguration;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@ConfigurationProperties(fileName = "config.yml", provider = YamlConfiguration.class)
public class MultiWarpConfiguration extends MultiWarpBukkitApiConfiguration {

    public MultiWarpConfiguration(@NotNull final ConfigurationHolder configurationHolder) {
        super(configurationHolder);
    }

    @Override
    public SimpleDateFormat getTimeFormat() {
        return new SimpleDateFormat(super.getFileConfiguration().getString(Entry.SETTINGS_TIME_FORMAT.getPath()));
    }

    @Override
    public boolean isMonitoringSystemEnabled() {
        return super.getBoolean(Entry.SETTINGS_MONITORING_SYSTEM_ENABLED.getPath());
    }

}
