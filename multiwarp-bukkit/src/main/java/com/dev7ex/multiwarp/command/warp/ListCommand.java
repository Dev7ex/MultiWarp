package com.dev7ex.multiwarp.command.warp;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import com.dev7ex.multiwarp.translation.DefaultTranslationProvider;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author Dev7ex
 * @since 16.08.2024
 */
@BukkitCommandProperties(name = "list", permission = "multiwarp.command.warp.list")
public class ListCommand extends BukkitCommand {

    public ListCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        final DefaultTranslationProvider translationProvider = MultiWarpPlugin.getInstance().getTranslationProvider();

        if (arguments.length != 1) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.list.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final Set<String> warpEntries = MultiWarpPlugin.getInstance().getWarpProvider().getWarps().keySet();

        for (final String warpEntry : warpEntries) {
            final BukkitWarp warp = MultiWarpPlugin.getInstance().getWarpProvider().getWarp(warpEntry).orElseThrow();

            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(ChatColor.GRAY);
                stringBuilder.append(", ");
            }
            stringBuilder.append(warp.isLocked() ? ChatColor.RED : ChatColor.GREEN).append(warpEntry);
        }
        commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.list.message")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%warp_names%", stringBuilder.toString()));
    }

}