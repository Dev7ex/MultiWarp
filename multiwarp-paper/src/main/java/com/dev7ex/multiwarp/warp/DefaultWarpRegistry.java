package com.dev7ex.multiwarp.warp;

import com.dev7ex.multiwarp.api.MultiWarpApi;
import com.dev7ex.multiwarp.api.warp.Warp;
import com.dev7ex.multiwarp.api.warp.WarpRegistry;
import com.dev7ex.multiwarp.module.DefaultWarpModule;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
public class DefaultWarpRegistry extends DefaultWarpModule implements WarpRegistry {

    private final Map<String, Warp> warps = new ConcurrentHashMap<>();

    public DefaultWarpRegistry(@NotNull final MultiWarpApi multiWarpApi) {
        super(multiWarpApi);
    }

    @Override
    public void onEnable() {
        this.warps.clear();
        this.multiWarpApi.getWarpRepository().sanitize();

        for (final Warp warp : this.multiWarpApi.getWarpRepository().loadAll()) {
            final Optional<Warp> registeredWarp = this.getWarp(warp.getName());

            if (registeredWarp.isPresent()) {
                this.multiWarpApi.getLogger().warning(String.format("Skipping Warp %s: name conflicts with %s",
                        warp.getName(), registeredWarp.get().getName()));
                continue;
            }
            this.register(warp);
        }
        this.multiWarpApi.getLogger().info(String.format("Found: [%d] Warps", this.warps.size()));
    }

    @Override
    public void onDisable() {
        this.warps.clear();
    }

    @Override
    public void register(@NotNull final Warp warp) {
        this.warps.put(this.toKey(warp.getName()), warp);
    }

    @Override
    public void unregister(@NotNull final String name) {
        this.warps.remove(this.toKey(name));
    }

    @Override
    public boolean isRegistered(@NotNull final String name) {
        return this.warps.containsKey(this.toKey(name));
    }

    @Override
    public @NotNull Optional<Warp> getWarp(@NotNull final String name) {
        return Optional.ofNullable(this.warps.get(this.toKey(name)));
    }

    @Override
    public @NotNull Collection<Warp> getWarps() {
        return this.warps.values().stream()
                .sorted()
                .toList();
    }

    private @NotNull String toKey(@NotNull final String name) {
        return name.toLowerCase(Locale.ROOT);
    }

}
