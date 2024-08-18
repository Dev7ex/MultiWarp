package com.dev7ex.multiwarp.command.warp;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.translation.DefaultTranslationProvider;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@BukkitCommandProperties(name = "help", permission = "multiwarp.command.warp")
public class HelpCommand extends BukkitCommand {

    public HelpCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        final DefaultTranslationProvider translationProvider = MultiWarpPlugin.getInstance().getTranslationProvider();

        translationProvider.getMessageList(commandSender, "commands.warp.help.message").forEach(message -> {
            commandSender.sendMessage(message.replaceAll("%prefix%", super.getConfiguration().getPrefix()));
        });
    }

}