package com.dev7ex.multiwarp.command.warp;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import com.dev7ex.multiwarp.translation.DefaultTranslationProvider;
import com.dev7ex.multiwarp.warp.DefaultWarpProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@BukkitCommandProperties(name = "teleport", permission = "multiwarp.command.warp.teleport")
public class TeleportCommand extends BukkitCommand implements BukkitTabCompleter {

    public TeleportCommand(@NotNull final BukkitPlugin plugin) {
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
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.teleport.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final DefaultWarpProvider warpProvider = MultiWarpPlugin.getInstance().getWarpProvider();

        if (!warpProvider.isRegistered(arguments[1])) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "general.warp.not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%warp_name%", arguments[1]));
            return;
        }
        final BukkitWarp warp = warpProvider.getWarp(arguments[1]).orElseThrow();

        if ((warp.isLocked()) && (!player.hasPermission("multiwarp.bypass"))) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "general.warp.locked")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%warp_name%", arguments[1]));
            return;
        }

        if ((warp.hasPermission()) && (!player.hasPermission(warp.getPermission()))) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.teleport.no-permission")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%warp_name%", arguments[1]));
            return;
        }

        if (Bukkit.getWorld(warp.getWorldName()) == null) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "general.warp.world-not-loaded")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%warp_name%", arguments[1]));
            return;
        }
        warpProvider.teleport(player, warp);
    }

    @Override
    public List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (commandSender.hasPermission("multiwarp.bypass")) {
            return new ArrayList<>(MultiWarpPlugin.getInstance().getWarpProvider().getWarps().keySet());
        }
        return MultiWarpPlugin.getInstance().getWarpProvider().getWarps()
                .values()
                .stream()
                .filter(bukkitWarp -> !bukkitWarp.isLocked())
                .map(BukkitWarp::getName)
                .toList();
    }

}