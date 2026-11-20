package com.dev7ex.multiwarp.command.warp;


import com.dev7ex.multicommon.api.command.PaperCommand;
import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import com.dev7ex.multicommon.api.command.PaperCommandProperties;
import com.dev7ex.multicommon.api.command.completer.EmptyTabCompleter;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationEntry;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import com.dev7ex.multiwarp.api.warp.WarpLocation;
import com.dev7ex.multiwarp.api.warp.WarpManager;
import com.dev7ex.multiwarp.api.warp.WarpRegistry;
import io.papermc.paper.registry.keys.SoundEventKeys;
import net.kyori.adventure.sound.Sound;
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
        if (!(commandSender instanceof final Player player)) {
            return;
        }

        if (arguments.length != 2) {
            return;
        }
        final String warpName = arguments[1].asString();

        this.warpRegistry.find(warpName).ifPresentOrElse(warp -> {
            commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.MESSAGE_WARP_ALREADY_EXISTS)
                    .replaceText(builder -> builder.match("%warp_colored_display_name%").replacement(warp.getColoredDisplayName())));

        }, () -> {
            this.warpManager.create(commandSender, warpName, WarpLocation.fromLocation(player.getLocation()));
            player.playSound(Sound.sound().type(SoundEventKeys.BLOCK_ANVIL_USE).build());
        });
    }

}
