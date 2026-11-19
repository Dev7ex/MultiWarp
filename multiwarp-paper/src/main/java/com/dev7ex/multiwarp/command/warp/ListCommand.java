package com.dev7ex.multiwarp.command.warp;

import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import com.dev7ex.multicommon.api.command.PaperCommand;
import com.dev7ex.multicommon.api.command.PaperCommandProperties;
import com.dev7ex.multicommon.api.command.completer.EmptyTabCompleter;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationEntry;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import com.dev7ex.multiwarp.api.warp.Warp;
import com.dev7ex.multiwarp.api.warp.WarpRegistry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Dev7ex
 * @since 16.08.2024
 */
@PaperCommandProperties(name = "list", permission = "multiwarp.command.warp.list")
public class ListCommand extends PaperCommand implements EmptyTabCompleter {

    private final WarpLocalizationRegistry localizationRegistry;
    private final WarpRegistry warpRegistry;

    public ListCommand(@NotNull final MultiWarpPlugin plugin) {
        super(plugin);
        this.localizationRegistry = plugin.getLocalizationRegistry();
        this.warpRegistry = plugin.getWarpRegistry();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        if (arguments.length != 1) {
            commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.COMMAND_WARP_LIST_USAGE));
            return;
        }
        final Collection<Warp> warps = new ArrayList<>();
        final TextComponent.Builder componentBuilder = Component.text();

        for (final Warp warp : warps) {
            if (!componentBuilder.children().isEmpty()) {
                componentBuilder.append(Component.text(", ", NamedTextColor.GRAY));
            }

            final boolean underline = commandSender instanceof Player
                    && ((Player) commandSender).getWorld().getName().equals(warp.getName());

            final TextComponent.Builder warpComponent = Component.text()
                    .content(warp.getDisplayName())
                    .clickEvent(ClickEvent.runCommand("/warp teleport " + warp.getName()));

            if (underline) {
                warpComponent.decorate(TextDecoration.UNDERLINED);
            }
            componentBuilder.append(warpComponent);
        }

        commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.COMMAND_WARP_LIST_MESSAGE)
                .replaceText(builder -> builder.match("%warp_display_names%").replacement(componentBuilder.build())));
    }
}
