package com.dev7ex.multiwarp.command.version;

import com.dev7ex.multicommon.api.command.PaperCommand;
import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import com.dev7ex.multicommon.api.command.PaperCommandProperties;
import com.dev7ex.multicommon.api.command.completer.EmptyTabCompleter;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationEntry;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@PaperCommandProperties(name = "check", permission = "multiwarp.command.warp.version.check")
public class CheckCommand extends PaperCommand implements EmptyTabCompleter {

    private final WarpLocalizationRegistry localizationRegistry;

    public CheckCommand(@NotNull final MultiWarpPlugin plugin) {
        super(plugin);
        this.localizationRegistry = plugin.getLocalizationRegistry();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        if (arguments.length != 2) {
            commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.COMMAND_WARP_VERSION_CHECK_USAGE));
            return;
        }
    }

}
