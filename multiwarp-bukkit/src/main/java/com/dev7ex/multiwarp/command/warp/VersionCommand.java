package com.dev7ex.multiwarp.command.warp;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 29.09.2024
 */
@BukkitCommandProperties(name = "version", permission = "multiwarp.command.warp.version")
public class VersionCommand extends BukkitCommand {

    public VersionCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        commandSender.sendMessage(" ");
        commandSender.sendMessage("§f§m                    §r§r " + super.getConfiguration().getPrefix() + " §f§m                    ");
        commandSender.sendMessage(" ");
        commandSender.sendMessage("§8» §eVersion: §a" + MultiWarpPlugin.getInstance().getDescription().getVersion());
        commandSender.sendMessage("§8» §eAuthors: " + this.getAuthors());
        commandSender.sendMessage("§8» §eSupport: §ahttps://discord.gg/ta33bbA8eF");
        commandSender.sendMessage("§8» §eWiki: §ahttps://github.com/Dev7ex/MultiWarp/wiki");
        commandSender.sendMessage("§8» §eReport Bug: §ahttps://github.com/Dev7ex/MultiWarp/issues");
        commandSender.sendMessage(" ");
        commandSender.sendMessage("§f§m                    §r§r " + super.getConfiguration().getPrefix() + " §f§m                    ");
        commandSender.sendMessage(" ");
    }

    private String getAuthors() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (final String author : MultiWarpPlugin.getInstance().getDescription().getAuthors()) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(ChatColor.GRAY);
                stringBuilder.append(", ");
            }
            stringBuilder.append(ChatColor.GREEN).append(author);
        }
        return stringBuilder.toString();
    }

}