package com.dev7ex.multiwarp.command.warp;

import com.dev7ex.multicommon.api.command.PaperCommand;
import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import com.dev7ex.multicommon.api.command.PaperCommandProperties;
import com.dev7ex.multicommon.api.command.completer.ArgumentTabCompleter;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationEntry;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import com.dev7ex.multiwarp.command.config.HelpCommand;
import com.dev7ex.multiwarp.command.config.ReloadCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Dev7ex
 * @since 20.11.2026
 */
@PaperCommandProperties(name = "config", permission = "multiwarp.command.warp.config")
public class ConfigCommand extends PaperCommand implements ArgumentTabCompleter {

    private final WarpLocalizationRegistry localizationRegistry;

    public ConfigCommand(@NotNull final MultiWarpPlugin plugin) {
        super(plugin);
        this.localizationRegistry = plugin.getLocalizationRegistry();

        this.registerSubCommand(new HelpCommand(plugin));
        this.registerSubCommand(new ReloadCommand(plugin));
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        if ((arguments.length == 1) || (arguments.length > 5)) {
            Objects.requireNonNull(super.findSubCommand(com.dev7ex.multiwarp.command.config.HelpCommand.class)).execute(commandSender, arguments);
            return;
        }
        final String subCommandName = arguments[1].asString().toLowerCase();

        super.findSubCommand(subCommandName).ifPresentOrElse(subCommand -> {
            if ((subCommand.hasPermission()) && (!commandSender.hasPermission(subCommand.getPermission()))) {
                commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.MESSAGE_NO_PERMISSION));
                return;
            }
            subCommand.execute(commandSender, arguments);

        }, () -> Objects.requireNonNull(super.findSubCommand(HelpCommand.class)).execute(commandSender, arguments));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        if ((arguments.length < 2) || (arguments.length > 4)) {
            return Collections.emptyList();
        }

        if (arguments.length == 2) {
            return super.getSubCommands().values().stream()
                    .filter(subCommand -> !subCommand.hasPermission() || commandSender.hasPermission(subCommand.getPermission()))
                    .map(subCommand -> subCommand.getName().toLowerCase())
                    .toList();
        }
        return super.findSubCommand(arguments[1].asString().toLowerCase())
                .filter(subCommand -> !subCommand.hasPermission() || commandSender.hasPermission(subCommand.getPermission()))
                .filter(ArgumentTabCompleter.class::isInstance)
                .map(subCommand -> ((ArgumentTabCompleter) subCommand).onTabComplete(commandSender, arguments))
                .orElse(Collections.emptyList());
    }
}
