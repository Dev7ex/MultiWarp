package com.dev7ex.multiwarp.command;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.command.warp.*;
import com.dev7ex.multiwarp.translation.DefaultTranslationProvider;
import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@BukkitCommandProperties(name = "warp", permission = "multiwarp.command.warp")
public class WarpCommand extends BukkitCommand implements BukkitTabCompleter {

    public WarpCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);

        super.registerSubCommand(new CreateCommand(plugin));
        super.registerSubCommand(new DeleteCommand(plugin));
        super.registerSubCommand(new FlagCommand(plugin));
        super.registerSubCommand(new HelpCommand(plugin));
        super.registerSubCommand(new InfoCommand(plugin));
        super.registerSubCommand(new ListCommand(plugin));
        super.registerSubCommand(new LockCommand(plugin));
        super.registerSubCommand(new TeleportCommand(plugin));
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        final DefaultTranslationProvider translationProvider = MultiWarpPlugin.getInstance().getTranslationProvider();

        if ((arguments.length == 0) || (arguments.length > 4)) {
            Objects.requireNonNull(super.getSubCommand(HelpCommand.class)).execute(commandSender, arguments);
            return;
        }
        if (super.getSubCommand(arguments[0].toLowerCase()).isEmpty()) {
            Objects.requireNonNull(super.getSubCommand(HelpCommand.class)).execute(commandSender, arguments);
            return;
        }
        final BukkitCommand subCommand = super.getSubCommand(arguments[0].toLowerCase()).get();

        if (!commandSender.hasPermission(subCommand.getPermission())) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "general.no-permission")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        super.getSubCommand(arguments[0].toLowerCase()).get().execute(commandSender, arguments);
    }

    @Override
    public List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (arguments.length == 1) {
            return Lists.newArrayList(super.getSubCommands().keySet());
        }
        if (super.getSubCommand(arguments[0].toLowerCase()).isEmpty()) {
            return Collections.emptyList();
        }
        final BukkitCommand subCommand = super.getSubCommand(arguments[0].toLowerCase()).get();

        if (!commandSender.hasPermission(subCommand.getPermission())) {
            return Collections.emptyList();
        }

        if (!(subCommand instanceof BukkitTabCompleter)) {
            return Collections.emptyList();
        }
        return ((BukkitTabCompleter) subCommand).onTabComplete(commandSender, arguments);
    }

}