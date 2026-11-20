
package com.dev7ex.multiwarp.command.config;

import com.dev7ex.multicommon.api.command.PaperCommand;
import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import com.dev7ex.multicommon.api.command.PaperCommandProperties;
import com.dev7ex.multicommon.api.command.completer.ArgumentTabCompleter;
import com.dev7ex.multiwarp.MultiWarpConfiguration;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationEntry;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Dev7ex
 * @since 20.11.2026
 */
@PaperCommandProperties(name = "help", permission = "multiwarp.command.warp.config.help")
public class HelpCommand extends PaperCommand implements ArgumentTabCompleter {

    private static final String COMMAND = "/warp config help ";
    private static final int DEFAULT_PAGE = 1;
    private static final int PAGE_ARGUMENT_INDEX = 3;

    private final MultiWarpConfiguration configuration;
    private final WarpLocalizationRegistry localizationRegistry;

    public HelpCommand(@NotNull final MultiWarpPlugin plugin) {
        super(plugin);
        this.configuration = plugin.getConfiguration();
        this.localizationRegistry = plugin.getLocalizationRegistry();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        if (arguments.length > (HelpCommand.PAGE_ARGUMENT_INDEX + 1)) {
            commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.COMMAND_WARP_CONFIG_HELP));
            return;
        }
        int page = HelpCommand.DEFAULT_PAGE;

        if (arguments.length > HelpCommand.PAGE_ARGUMENT_INDEX) {
            try {
                page = Integer.parseInt(arguments[HelpCommand.PAGE_ARGUMENT_INDEX].asString());
            } catch (final NumberFormatException exception) {
                commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.MESSAGE_PAGINATION_NOT_FOUND)
                        .replaceText(builder -> builder.match("%page%").replacement(arguments[1].asString())));
                return;
            }
        }
        final WarpLocalizationEntry entry = WarpLocalizationEntry.COMMAND_WARP_CONFIG_HELP;
        final int maxAvailablePages = this.localizationRegistry.resolveMaxPages(commandSender, entry);

        if ((page < 1) || (page > maxAvailablePages)) {
            commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.MESSAGE_PAGINATION_OUT_OF_RANGE)
                    .replaceText(builder -> builder.matchLiteral("%maximum_pages%").replacement(String.valueOf(maxAvailablePages))));
            return;
        }
        commandSender.sendMessage(this.localizationRegistry.createHeader(commandSender, this.configuration.getPrefix()));
        this.localizationRegistry.resolveComponents(commandSender, entry.getKeyForPage(page)).forEach(commandSender::sendMessage);
        commandSender.sendMessage(Component.empty());
        commandSender.sendMessage(this.localizationRegistry.createPagination(commandSender, page, HelpCommand.COMMAND, entry));
        commandSender.sendMessage(this.localizationRegistry.createFooter(commandSender, this.configuration.getPrefix()));
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        if (arguments.length != (HelpCommand.PAGE_ARGUMENT_INDEX + 1)) {
            return Collections.emptyList();
        }
        final int maxPages = this.localizationRegistry.resolveMaxPages(commandSender, WarpLocalizationEntry.COMMAND_WARP_CONFIG_HELP);

        return IntStream.rangeClosed(1, maxPages)
                .mapToObj(String::valueOf)
                .toList();
    }

}
