package com.dev7ex.multiwarp;

import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.common.bukkit.plugin.ConfigurablePlugin;
import com.dev7ex.common.bukkit.plugin.statistic.PluginStatisticProperties;
import com.dev7ex.multiwarp.api.MultiWarpApiProvider;
import com.dev7ex.multiwarp.api.bukkit.MultiWarpBukkitApi;
import com.dev7ex.multiwarp.command.WarpCommand;
import com.dev7ex.multiwarp.translation.DefaultTranslationProvider;
import com.dev7ex.multiwarp.warp.DefaultWarpConfiguration;
import com.dev7ex.multiwarp.warp.DefaultWarpProvider;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@Getter(AccessLevel.PUBLIC)
@PluginStatisticProperties(enabled = true, identification = 23035)
public class MultiWarpPlugin extends BukkitPlugin implements ConfigurablePlugin, MultiWarpBukkitApi {

    private MultiWarpConfiguration configuration;
    private DefaultWarpConfiguration warpConfiguration;

    private DefaultWarpProvider warpProvider;
    private DefaultTranslationProvider translationProvider;

    @Override
    public void onLoad() {
        super.createDataFolder();
        super.createSubFolder("language");

        this.configuration = new MultiWarpConfiguration(this);
        this.configuration.load();

        this.warpConfiguration = new DefaultWarpConfiguration(this);
        this.warpConfiguration.createFile();
        this.warpConfiguration.loadFile();
    }

    @Override
    public void onEnable() {
        super.getServer().getServicesManager().register(MultiWarpBukkitApi.class, this, this, ServicePriority.Normal);

        MultiWarpApiProvider.registerApi(this);
    }

    @Override
    public void onDisable() {
        MultiWarpApiProvider.unregisterApi();
    }

    @Override
    public void registerCommands() {
        super.registerCommand(new WarpCommand(this));
    }

    @Override
    public void registerListeners() {

    }

    @Override
    public void registerModules() {
        super.registerModule(this.warpProvider = new DefaultWarpProvider(this.warpConfiguration));
        super.registerModule(this.translationProvider = new DefaultTranslationProvider(this));
    }

    public static MultiWarpPlugin getInstance() {
        return JavaPlugin.getPlugin(MultiWarpPlugin.class);
    }

}
