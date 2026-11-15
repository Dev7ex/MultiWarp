package com.dev7ex.multiwarp;

import com.dev7ex.multicommon.api.plugin.AdvancedPlugin;
import com.dev7ex.multicommon.api.plugin.PluginFileMonitor;
import com.dev7ex.multiwarp.api.MultiWarpApi;
import com.dev7ex.multiwarp.api.MultiWarpApiProvider;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import com.dev7ex.multiwarp.api.module.WarpModuleManager;
import com.dev7ex.multiwarp.api.permission.MultiWarpPermission;
import com.dev7ex.multiwarp.api.warp.WarpFactory;
import com.dev7ex.multiwarp.api.warp.WarpRegistry;
import com.dev7ex.multiwarp.api.warp.WarpRepository;
import com.dev7ex.multiwarp.command.WarpCommand;
import com.dev7ex.multiwarp.listener.plugin.MultiWarpFileChangeListener;
import com.dev7ex.multiwarp.localization.DefaultWarpLocalizationRegistry;
import com.dev7ex.multiwarp.module.DefaultWarpModuleManager;
import com.dev7ex.multiwarp.warp.DefaultWarpFactory;
import com.dev7ex.multiwarp.warp.DefaultWarpRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.ServicePriority;

import java.io.File;
import java.util.Arrays;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class MultiWarpPlugin extends AdvancedPlugin implements MultiWarpApi {

    private MultiWarpConfiguration configuration;

    private WarpRepository warpRepository;

    private WarpModuleManager moduleManager;

    private WarpLocalizationRegistry localizationRegistry;
    private WarpRegistry warpRegistry;

    private WarpFactory warpFactory;

    private File languageDirectory;
    private File logDirectory;

    private PluginFileMonitor fileMonitor;

    @Override
    public void onLoad() {
        this.createDataDirectory();

        this.languageDirectory = super.createSubDirectory("language");
        this.logDirectory = this.createSubDirectory("log");

        this.configuration = new MultiWarpConfiguration(this);
        this.configuration.copyFile();
        this.configuration.loadFile();

        this.registerRepositories();
    }

    @Override
    public void onEnable() {
        MultiWarpApiProvider.register(this);

        this.fileMonitor = new PluginFileMonitor(this, new MultiWarpFileChangeListener(this));
        this.fileMonitor.start();

        super.getServer().getServicesManager().register(MultiWarpApi.class, this, this, ServicePriority.Normal);

        Arrays.stream(MultiWarpPermission.values())
                .map(MultiWarpPermission::asBukkit)
                .filter(permission -> super.getServer().getPluginManager().getPermission(permission.getName()) == null)
                .forEach(permission -> super.getServer().getPluginManager().addPermission(permission));

        this.registerManagers();

        this.registerModules();
        this.registerFactories();
        this.registerCommands();
        this.registerListeners();
        this.registerTasks();

        this.moduleManager.enableAll();
    }

    @Override
    public void onDisable() {
        this.moduleManager.disableAll();

        MultiWarpApiProvider.unregister();
        super.getServer().getServicesManager().unregisterAll(this);

        this.fileMonitor.stop();
    }

    @Override
    public void registerCommands() {
        this.registerCommand(new WarpCommand(this));
    }

    @Override
    public void registerFactories() {
        this.warpFactory = new DefaultWarpFactory();
    }

    @Override
    public void registerManagers() {
        this.moduleManager = new DefaultWarpModuleManager();
    }

    @Override
    public void registerModules() {
        this.moduleManager.register(this.localizationRegistry = new DefaultWarpLocalizationRegistry(this));
    }

    @Override
    public void registerRepositories() {
        this.warpRepository = new DefaultWarpRepository(this);
        this.warpRepository.createFile();
        this.warpRepository.loadFile();
    }

}
