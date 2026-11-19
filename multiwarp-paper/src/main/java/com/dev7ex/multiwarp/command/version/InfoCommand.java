package com.dev7ex.multiwarp.command.version;

import com.dev7ex.multicommon.api.command.PaperCommand;
import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import com.dev7ex.multicommon.api.command.PaperCommandProperties;
import com.dev7ex.multicommon.api.command.completer.ArgumentTabCompleter;
import com.dev7ex.multiwarp.MultiWarpConfiguration;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationEntry;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Dev7ex
 * @since 20.11.2026
 */
@PaperCommandProperties(name = "info", permission = "multiwarp.command.warp.version.info")
public class InfoCommand extends PaperCommand implements ArgumentTabCompleter {

    private final MultiWarpConfiguration configuration;
    private final WarpLocalizationRegistry localizationRegistry;

    public InfoCommand(@NotNull final MultiWarpPlugin plugin) {
        super(plugin);
        this.configuration = plugin.getConfiguration();
        this.localizationRegistry = plugin.getLocalizationRegistry();
    }


    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {

    }

    @Override
    public List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        if (arguments.length != 3) {
            return Collections.emptyList();
        }
        final int maxPages = this.localizationRegistry.resolveMaxPages(commandSender, WarpLocalizationEntry.COMMAND_WARP_VERSION_INFO_MESSAGE);

        return IntStream.rangeClosed(1, maxPages)
                .mapToObj(String::valueOf)
                .toList();
    }

    private String getAuthors() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (final String author : MultiWarpPlugin.getInstance().getPluginMeta().getAuthors()) {
            if (author.equalsIgnoreCase("Dev7ex")) {
                continue;
            }
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(NamedTextColor.GRAY);
                stringBuilder.append(", ");
            }
            stringBuilder.append(NamedTextColor.GREEN).append(author);
        }

        if (stringBuilder.isEmpty()) {
            return "[]";
        }

        return stringBuilder.toString();
    }

}
