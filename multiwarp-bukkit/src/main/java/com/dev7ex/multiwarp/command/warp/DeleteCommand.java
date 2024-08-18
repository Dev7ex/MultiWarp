package com.dev7ex.multiwarp.command.warp;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import com.dev7ex.multiwarp.translation.DefaultTranslationProvider;
import com.dev7ex.multiwarp.warp.DefaultWarpConfiguration;
import com.dev7ex.multiwarp.warp.DefaultWarpProvider;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@BukkitCommandProperties(name = "delete", permission = "multiwarp.command.warp.delete")
public class DeleteCommand extends BukkitCommand implements BukkitTabCompleter {

    public DeleteCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        final DefaultTranslationProvider translationProvider = MultiWarpPlugin.getInstance().getTranslationProvider();

        if (arguments.length != 2) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.delete.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final DefaultWarpConfiguration warpConfiguration = MultiWarpPlugin.getInstance().getWarpConfiguration();
        final DefaultWarpProvider warpProvider = MultiWarpPlugin.getInstance().getWarpProvider();

        if (!warpProvider.isRegistered(arguments[1])) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "general.warp.not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%warp_name%", arguments[1]));
            return;
        }
        final BukkitWarp warp = warpProvider.getWarp(arguments[1]).orElseThrow();

        warpConfiguration.remove(warp);
        warpProvider.unregister(warp);

        commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.delete.successfully-deleted")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%warp_name%", arguments[1]));
    }

    @Override
    public List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        return new ArrayList<>(MultiWarpPlugin.getInstance().getWarpProvider().getWarps().keySet());
    }

}