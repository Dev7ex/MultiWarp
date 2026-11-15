package com.dev7ex.multiwarp;

import com.dev7ex.multicommon.api.configuration.Configuration;
import com.dev7ex.multicommon.api.configuration.ConfigurationHolder;
import com.dev7ex.multicommon.api.configuration.ConfigurationProperties;
import com.dev7ex.multicommon.api.configuration.JsonConfiguration;
import com.dev7ex.multicommon.api.text.TextFormatter;
import com.dev7ex.multiwarp.api.MultiWarpApiConfiguration;
import lombok.AccessLevel;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
@Getter(AccessLevel.PUBLIC)
@ConfigurationProperties(fileName = "config.json", provider = JsonConfiguration.class)
public class MultiWarpConfiguration extends Configuration implements MultiWarpApiConfiguration {

    public MultiWarpConfiguration(@NotNull final ConfigurationHolder configurationHolder) {
        super(configurationHolder);
    }

    @Override
    public @NotNull Component getPrefix() {
        return TextFormatter.parse(super.getFileConfiguration().getString("prefix"));
    }

}
