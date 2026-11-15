package com.dev7ex.multiwarp.warp;

import com.dev7ex.multicommon.api.configuration.Configuration;
import com.dev7ex.multicommon.api.configuration.ConfigurationHolder;
import com.dev7ex.multicommon.api.configuration.ConfigurationProperties;
import com.dev7ex.multicommon.api.configuration.JsonConfiguration;
import com.dev7ex.multiwarp.api.warp.WarpRepository;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
@ConfigurationProperties(fileName = "warp.json", provider = JsonConfiguration.class)
public class DefaultWarpRepository extends Configuration implements WarpRepository {

    public DefaultWarpRepository(@NotNull final ConfigurationHolder configurationHolder) {
        super(configurationHolder);
    }

}
