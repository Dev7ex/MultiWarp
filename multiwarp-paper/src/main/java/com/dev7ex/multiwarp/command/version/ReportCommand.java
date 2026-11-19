package com.dev7ex.multiwarp.command.version;

import com.dev7ex.multicommon.api.command.PaperCommand;
import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import com.dev7ex.multicommon.api.command.PaperCommandProperties;
import com.dev7ex.multicommon.api.plugin.AdvancedPlugin;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 20.11.2026
 */
@PaperCommandProperties(name = "report", permission = "multiwarp.command.warp.version.report")
public class ReportCommand extends PaperCommand {

    public ReportCommand(@NotNull final AdvancedPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull CommandSender commandSender, @NotNull PaperCommandArgument[] paperCommandArguments) {

    }

}
