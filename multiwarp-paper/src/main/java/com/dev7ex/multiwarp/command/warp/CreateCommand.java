package com.dev7ex.multiwarp.command.warp;


import com.dev7ex.multicommon.api.command.PaperCommand;
import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import com.dev7ex.multicommon.api.command.PaperCommandProperties;
import com.dev7ex.multicommon.api.command.completer.EmptyTabCompleter;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import com.dev7ex.multiwarp.api.warp.WarpFactory;
import com.dev7ex.multiwarp.api.warp.WarpManager;
import com.dev7ex.multiwarp.api.warp.WarpRegistry;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.08.2024
 */
@PaperCommandProperties(name = "create", permission = "multiwarp.command.warp.create")
public class CreateCommand extends PaperCommand implements EmptyTabCompleter {

    private final WarpLocalizationRegistry localizationRegistry;
    private final WarpManager warpManager;
    private final WarpRegistry warpRegistry;

    public CreateCommand(@NotNull final MultiWarpPlugin plugin) {
        super(plugin);
        this.localizationRegistry = plugin.getLocalizationRegistry();
        this.warpManager = plugin.getWarpManager();
        this.warpRegistry = plugin.getWarpRegistry();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        if (!(commandSender instanceof Player player)) {
            return;
        }

        if (arguments.length != 2) {
            return;
        }
        final String warpName = arguments[1].asString();

        this.warpRegistry.find(warpName).ifPresentOrElse(warp -> {



        }, () -> {

        });
    }

}
