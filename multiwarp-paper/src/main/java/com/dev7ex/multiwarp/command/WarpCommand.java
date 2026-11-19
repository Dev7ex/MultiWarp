package com.dev7ex.multiwarp.command;

import com.dev7ex.multicommon.api.command.PaperCommand;
import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import com.dev7ex.multicommon.api.command.PaperCommandProperties;
import com.dev7ex.multicommon.api.command.completer.ArgumentTabCompleter;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationEntry;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import com.dev7ex.multiwarp.command.warp.*;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@PaperCommandProperties(name = "warp", permission = "multiwarp.command.warp")
public class WarpCommand extends PaperCommand implements ArgumentTabCompleter {

    private final WarpLocalizationRegistry localizationRegistry;

    public WarpCommand(@NotNull final MultiWarpPlugin plugin) {
        super(plugin);
        this.localizationRegistry = plugin.getLocalizationRegistry();

        this.registerSubCommand(new ConfigCommand(plugin));
        this.registerSubCommand(new CreateCommand(plugin));
        this.registerSubCommand(new DeleteCommand(plugin));
        this.registerSubCommand(new HelpCommand(plugin));
        this.registerSubCommand(new InfoCommand(plugin));
        this.registerSubCommand(new ListCommand(plugin));
        this.registerSubCommand(new LockCommand(plugin));
        this.registerSubCommand(new TeleportCommand(plugin));
        this.registerSubCommand(new VersionCommand(plugin));
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        if ((arguments.length == 0) || (arguments.length > 5)) {
            Objects.requireNonNull(super.findSubCommand(HelpCommand.class)).execute(commandSender, arguments);
            return;
        }
        final String subCommandName = arguments[0].asString().toLowerCase();

        super.findSubCommand(subCommandName).ifPresentOrElse(subCommand -> {
            if (!commandSender.hasPermission(subCommand.getPermission())) {
                commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.MESSAGE_NO_PERMISSION));
                return;
            }
            subCommand.execute(commandSender, arguments);

        }, () -> Objects.requireNonNull(super.findSubCommand(HelpCommand.class)).execute(commandSender, arguments));
    }

    @Override
    public List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        if (arguments.length == 1) {
            return new ArrayList<>(super.getSubCommands().keySet());
        }
        final String subCommandName = arguments[0].asString().toLowerCase();

        return super.findSubCommand(subCommandName)
                .filter(sub -> commandSender.hasPermission(sub.getPermission()))
                .filter(sub -> sub instanceof ArgumentTabCompleter)
                .map(sub -> ((ArgumentTabCompleter) sub).onTabComplete(commandSender, arguments))
                .orElse(Collections.emptyList());
    }

}
