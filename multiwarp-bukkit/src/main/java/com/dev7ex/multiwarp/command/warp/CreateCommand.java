package com.dev7ex.multiwarp.command.warp;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.bukkit.event.warp.WarpCreateEvent;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import com.dev7ex.multiwarp.translation.DefaultTranslationProvider;
import com.dev7ex.multiwarp.warp.DefaultWarpConfiguration;
import com.dev7ex.multiwarp.warp.DefaultWarpProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@BukkitCommandProperties(name = "create", permission = "multiwarp.command.warp.create")
public class CreateCommand extends BukkitCommand {

    public CreateCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        final DefaultTranslationProvider translationProvider = MultiWarpPlugin.getInstance().getTranslationProvider();

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "general.no-console-command")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%command_name%", super.getName()));
            return;
        }
        final Player player = (Player) commandSender;

        if (arguments.length != 2) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.create.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final DefaultWarpConfiguration warpConfiguration = MultiWarpPlugin.getInstance().getWarpConfiguration();
        final DefaultWarpProvider warpProvider = MultiWarpPlugin.getInstance().getWarpProvider();

        if (warpProvider.isRegistered(arguments[1])) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "general.warp.already-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%warp_name%", arguments[1]));
            return;
        }
        final Location location = player.getLocation();
        final BukkitWarp warp = BukkitWarp.builder()
                .setName(arguments[1])
                .setCreatorName(player.getName())
                .setCreationTimeStamp(System.currentTimeMillis())
                .setLocked(false)
                .setPermission("")
                .setWorldName(player.getWorld().getName())
                .setX(Math.round(location.getX()))
                .setY(Math.round(location.getY()))
                .setZ(Math.round(location.getZ()))
                .setPitch(location.getPitch())
                .setYaw(location.getYaw())
                .build();

        Bukkit.getPluginManager().callEvent(new WarpCreateEvent(warp));

        warpConfiguration.add(warp);
        warpProvider.register(warp);
        commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.create.successfully-created")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%warp_name%", arguments[1]));
    }

}