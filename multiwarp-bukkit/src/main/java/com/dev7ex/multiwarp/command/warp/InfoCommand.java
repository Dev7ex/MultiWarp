package com.dev7ex.multiwarp.command.warp;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiwarp.MultiWarpConfiguration;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import com.dev7ex.multiwarp.translation.DefaultTranslationProvider;
import com.dev7ex.multiwarp.warp.DefaultWarpProvider;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@BukkitCommandProperties(name = "info", permission = "multiwarp.command.warp.info")
public class InfoCommand extends BukkitCommand implements BukkitTabCompleter {

    public InfoCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        final DefaultTranslationProvider translationProvider = MultiWarpPlugin.getInstance().getTranslationProvider();

        if (arguments.length != 2) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.info.usage")
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
        final MultiWarpConfiguration configuration = MultiWarpPlugin.getInstance().getConfiguration();

        translationProvider.getMessageList(commandSender, "commands.warp.info.message").forEach(message -> {
            commandSender.sendMessage(message.replaceAll("%warp_name%", warp.getName())
                    .replaceAll("%warp_creator_name%", warp.getCreatorName())
                    .replaceAll("%creation_timestamp%", configuration.getTimeFormat().format(new Date(warp.getCreationTimeStamp())))
                    .replaceAll("%colored_locked%", (warp.isLocked() ? "§atrue" : "§cfalse"))
                    .replaceAll("%locked%", (warp.isLocked() ? "true" : "false"))
                    .replaceAll("%permission%", warp.getPermission().isBlank() ? "§a[]" : ChatColor.GREEN + warp.getPermission())
                    .replaceAll("%world_name%", warp.getWorldName())
                    .replaceAll("%x%", String.valueOf(warp.getX()))
                    .replaceAll("%y%", String.valueOf(warp.getY()))
                    .replaceAll("%z%", String.valueOf(warp.getZ()))
                    .replaceAll("%pitch%", String.valueOf(warp.getPitch()))
                    .replaceAll("%yaw%", String.valueOf(warp.getYaw())));
        });
    }

    @Override
    public List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        return new ArrayList<>(MultiWarpPlugin.getInstance().getWarpProvider().getWarps().keySet());
    }

}