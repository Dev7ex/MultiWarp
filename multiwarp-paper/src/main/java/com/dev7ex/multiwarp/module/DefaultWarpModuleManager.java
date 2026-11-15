package com.dev7ex.multiwarp.module;

import com.dev7ex.multiwarp.api.module.WarpModule;
import com.dev7ex.multiwarp.api.module.WarpModuleManager;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Dev7ex
 * @since 13.04.2025
 */
@Getter(AccessLevel.PUBLIC)
public class DefaultWarpModuleManager implements WarpModuleManager {

    private final LinkedList<WarpModule> registeredModules = new LinkedList<>();
    private final Map<UUID, WarpModule> modules = new HashMap<>();

    @Override
    public void register(@NotNull final WarpModule module) {
        this.registeredModules.add(module);
        this.modules.put(module.getUniqueId(), module);
    }

    @Override
    public void unregister(@NotNull final WarpModule module) {
        this.registeredModules.remove(module);
        this.modules.remove(module.getUniqueId());
    }

    @Override
    public void unregister(@NotNull final Class<? extends WarpModule> moduleClazz) {
        final Optional<WarpModule> moduleOptional = this.find(moduleClazz);

        if (moduleOptional.isEmpty()) {
            return;
        }
        this.unregister(moduleOptional.get());
    }

    @Override
    public Optional<WarpModule> find(@NotNull final Class<? extends WarpModule> moduleClazz) {
        return this.modules.values().stream()
                .filter(module -> module.getClass().equals(moduleClazz))
                .findFirst();
    }

    @Override
    public void disable(@NotNull final WarpModule module) {
        module.onDisable();
    }

    @Override
    public void disable(@NotNull final Class<? extends WarpModule> moduleClazz) {
        this.modules.values().stream()
                .filter(m -> m.getClass().equals(moduleClazz))
                .findFirst()
                .ifPresent(WarpModule::onDisable);
    }

    @Override
    public void enableAll() {
        while (!this.registeredModules.isEmpty()) {
            final WarpModule module = this.registeredModules.pollFirst();
            if (module != null) {
                module.onEnable();
            }
        }
    }

    @Override
    public void disableAll() {
        for (final WarpModule module : new ArrayList<>(this.modules.values())) {
            if (module != null) {
                module.onDisable();
            }
        }
        this.modules.clear();
    }

}
