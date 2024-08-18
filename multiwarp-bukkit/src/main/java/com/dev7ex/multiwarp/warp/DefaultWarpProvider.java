package com.dev7ex.multiwarp.warp;

import com.dev7ex.common.bukkit.plugin.module.PluginModule;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.bukkit.event.player.PlayerTeleportWarpEvent;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarpConfiguration;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarpProvider;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@Getter(AccessLevel.PUBLIC)
public class DefaultWarpProvider implements PluginModule, BukkitWarpProvider {

    private final Map<String, BukkitWarp> warps = new HashMap<>();
    private final BukkitWarpConfiguration configuration;

    public DefaultWarpProvider(@NotNull final BukkitWarpConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void onEnable() {
        this.warps.putAll(this.configuration.getWarps());

        MultiWarpPlugin.getInstance().getLogger().info("Found: [" + this.warps.size() + "] Warps");
    }

    @Override
    public void onDisable() {
        this.warps.clear();
    }

    @Override
    public void register(@NotNull final BukkitWarp warp) {
        this.warps.put(warp.getName(), warp);
    }

    @Override
    public void unregister(@NotNull final BukkitWarp warp) {
        this.warps.remove(warp.getName());
    }

    @Override
    public void unregister(@NotNull final String name) {
        this.warps.remove(name);
    }

    @Override
    public boolean isRegistered(@NotNull final BukkitWarp warp) {
        return this.warps.containsKey(warp.getName());
    }

    @Override
    public boolean isRegistered(@NotNull final String name) {
        return this.warps.containsKey(name);
    }

    @Override
    public void teleport(@NotNull final Player entity, @NotNull final BukkitWarp warp) {
        final PlayerTeleportWarpEvent event = new PlayerTeleportWarpEvent(entity, warp);

        if (event.isCancelled()) {
            return;
        }
        entity.teleport(event.getWarp().toLocation());
        entity.playSound(entity.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
    }

    @Override
    public Optional<BukkitWarp> getWarp(@NotNull final String name) {
        return Optional.ofNullable(this.warps.get(name));
    }

}
