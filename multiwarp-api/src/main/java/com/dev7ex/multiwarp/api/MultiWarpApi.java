package com.dev7ex.multiwarp.api;

import com.dev7ex.multicommon.api.configuration.ConfigurationHolder;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import com.dev7ex.multiwarp.api.module.WarpModuleManager;
import com.dev7ex.multiwarp.api.warp.WarpFactory;
import com.dev7ex.multiwarp.api.warp.WarpManager;
import com.dev7ex.multiwarp.api.warp.WarpRegistry;
import com.dev7ex.multiwarp.api.warp.WarpRepository;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
public interface MultiWarpApi extends Plugin, ConfigurationHolder {

    @NotNull
    default NamespacedKey createKey(@NotNull final String key) {
        return new NamespacedKey(this, key);
    }

    @NotNull
    MultiWarpApiConfiguration getConfiguration();

    @Nullable
    File getLanguageDirectory();

    void setLanguageDirectory(@NotNull final File languageDirectory);

    @Nullable
    File getLogDirectory();

    void setLogDirectory(@NotNull final File logDirectory);

    @NotNull
    WarpLocalizationRegistry getLocalizationRegistry();

    void setLocalizationRegistry(@NotNull final WarpLocalizationRegistry localizationRegistry);

    @NotNull
    WarpModuleManager getModuleManager();

    void setModuleManager(@NotNull final WarpModuleManager moduleManager);

    WarpFactory getWarpFactory();

    void setWarpFactory(@NotNull final WarpFactory warpFactory);

    @NotNull
    WarpManager getWarpManager();

    void setWarpManager(@NotNull final WarpManager warpManager);

    WarpRegistry getWarpRegistry();

    void setWarpRegistry(@NotNull final WarpRegistry warpRegistry);

    WarpRepository getWarpRepository();

    void setWarpRepository(@NotNull final WarpRepository warpRepository);

}
