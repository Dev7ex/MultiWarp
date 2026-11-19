package com.dev7ex.multiwarp.command.config;

import com.dev7ex.multicommon.api.command.PaperCommand;
import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import com.dev7ex.multicommon.api.command.PaperCommandProperties;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 20.11.2026
 */
@PaperCommandProperties(name = "reload", permission = "multiwarp.command.warp.config.reload")
public class ReloadCommand extends PaperCommand {

    public ReloadCommand(@NotNull final MultiWarpPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull CommandSender commandSender, @NotNull PaperCommandArgument[] arguments) {

    }

}
