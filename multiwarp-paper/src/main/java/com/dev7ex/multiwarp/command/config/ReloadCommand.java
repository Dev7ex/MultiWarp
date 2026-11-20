
package com.dev7ex.multiwarp.command.config;

import com.dev7ex.multicommon.api.command.PaperCommand;
import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import com.dev7ex.multicommon.api.command.PaperCommandProperties;
import com.dev7ex.multiwarp.MultiWarpConfiguration;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationEntry;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import io.papermc.paper.registry.keys.SoundEventKeys;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 20.11.2026
 */
@PaperCommandProperties(name = "reload", permission = "multiwarp.command.warp.config.reload")
public class ReloadCommand extends PaperCommand {

    private final MultiWarpPlugin plugin;
    private final MultiWarpConfiguration configuration;
    private final WarpLocalizationRegistry localizationRegistry;

    public ReloadCommand(@NotNull final MultiWarpPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
        this.configuration = plugin.getConfiguration();
        this.localizationRegistry = plugin.getLocalizationRegistry();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        if (arguments.length != 3) {
            commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.COMMAND_WARP_CONFIG_RELOAD_USAGE));
            return;
        }
        commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.COMMAND_WARP_CONFIG_RELOAD_STARTING));
        commandSender.playSound(Sound.sound().type(SoundEventKeys.BLOCK_NOTE_BLOCK_HARP).build());

        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            try {
                this.configuration.loadFile();

                commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.COMMAND_WARP_CONFIG_RELOAD_FINISHING));
                commandSender.playSound(Sound.sound().type(SoundEventKeys.BLOCK_NOTE_BLOCK_PLING).build());

            } catch (final Exception exception) {
                this.plugin.getLogger().severe("Failed to reload the configuration: " + exception.getMessage());
                //commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.COMMAND_WARP_CONFIG_RELOAD_USAGE)); TODO: error message
            }
        }, 10L);
    }

}
