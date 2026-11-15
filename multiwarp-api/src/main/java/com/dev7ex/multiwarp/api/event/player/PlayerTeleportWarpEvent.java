package com.dev7ex.multiwarp.api.event.player;

import com.dev7ex.multiwarp.api.warp.Warp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class PlayerTeleportWarpEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;
    private boolean notifyOnCancel = true;
    private final Warp warp;

    public PlayerTeleportWarpEvent(@NotNull final Player player, @NotNull final Warp warp) {
        super(player);
        this.warp = warp;
    }

    public static HandlerList getHandlerList() {
        return PlayerTeleportWarpEvent.HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return PlayerTeleportWarpEvent.HANDLERS;
    }

}
