package com.dev7ex.multiwarp.api.bukkit.warp;

import com.dev7ex.multiwarp.api.warp.WarpProvider;
import org.bukkit.entity.Player;

/**
 * Interface for providing and managing warps specific to the Bukkit environment.
 * Extends WarpProvider to handle BukkitWarp instances and Player entities.
 *
 * @author Dev7ex
 * @since 15.08.2024
 */
public interface BukkitWarpProvider extends WarpProvider<BukkitWarp, Player> {
}
