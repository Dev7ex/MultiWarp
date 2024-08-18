package com.dev7ex.multiwarp.api.bukkit.event.player;

import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * This event is triggered when a player is teleported to a specific warp location.
 * It is cancellable, allowing you to prevent the teleport if necessary.
 *
 * @author Dev7ex
 * @since 16.08.2024
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class PlayerTeleportWarpEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;
    private final BukkitWarp warp;

    /**
     * Constructs a new PlayerTeleportWarpEvent.
     *
     * @param player The player being teleported.
     * @param warp The warp location the player is being teleported to.
     */
    public PlayerTeleportWarpEvent(@NotNull final Player player, @NotNull final BukkitWarp warp) {
        super(player);
        this.warp = warp;
    }

    /**
     * Returns the HandlerList for this event.
     *
     * @return The handler list.
     */
    public static HandlerList getHandlerList() {
        return PlayerTeleportWarpEvent.HANDLERS;
    }

    /**
     * Returns the handlers for this event.
     *
     * @return The handler list.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return PlayerTeleportWarpEvent.HANDLERS;
    }

}
