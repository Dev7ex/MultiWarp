package com.dev7ex.multiwarp.command.warp;

import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import com.dev7ex.multicommon.api.command.PaperCommand;
import com.dev7ex.multicommon.api.command.PaperCommandProperties;
import com.dev7ex.multicommon.api.command.completer.ArgumentTabCompleter;
import com.dev7ex.multiwarp.MultiWarpConfiguration;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationEntry;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@PaperCommandProperties(name = "help", permission = "multiwarp.command.warp")
public class HelpCommand extends PaperCommand implements ArgumentTabCompleter {

    private final MultiWarpConfiguration configuration;
    private final WarpLocalizationRegistry localizationRegistry;

    public HelpCommand(@NotNull final MultiWarpPlugin plugin) {
        super(plugin);
        this.configuration = plugin.getConfiguration();
        this.localizationRegistry = plugin.getLocalizationRegistry();
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        final int page;
        final String command = "/warp help ";

        if (arguments.length > 2) {
            commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.COMMAND_WARP_HELP_USAGE));
            return;
        }

        if ((arguments.length == 0) || (arguments.length == 1)) {
            page = 1;

        } else {
            final String pageArgument = arguments[1].asString();

            try {
                page = Integer.parseInt(pageArgument);

            } catch (final NumberFormatException ignored) {
                commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.MESSAGE_PAGINATION_NOT_FOUND)
                        .replaceText(builder -> builder.match("%page%").replacement(pageArgument)));
                return;
            }
        }
        final int maxAvailablePages = this.localizationRegistry.resolveMaxPages(commandSender, WarpLocalizationEntry.COMMAND_WARP_HELP);

        if ((page < 1) || (page > maxAvailablePages)) {
            commandSender.sendMessage(this.localizationRegistry.resolveComponent(commandSender, WarpLocalizationEntry.MESSAGE_PAGINATION_OUT_OF_RANGE)
                    .replaceText(builder -> builder.match("%maximum_pages%").replacement(String.valueOf(maxAvailablePages))));
            return;
        }
        commandSender.sendMessage(this.localizationRegistry.createHeader(commandSender, this.configuration.getPrefix()));
        this.localizationRegistry.resolveComponents(commandSender, WarpLocalizationEntry.COMMAND_WARP_HELP.getKeyForPage(page)).forEach(commandSender::sendMessage);
        commandSender.sendMessage(this.localizationRegistry.createPagination(commandSender, page, command, WarpLocalizationEntry.COMMAND_WARP_HELP));
        commandSender.sendMessage(this.localizationRegistry.createFooter(commandSender, this.configuration.getPrefix()));
    }

    @Override
    public List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        if (arguments.length != 2) {
            return Collections.emptyList();
        }
        final int maxPages = this.localizationRegistry.resolveMaxPages(commandSender, WarpLocalizationEntry.COMMAND_WARP_HELP);

        return IntStream.rangeClosed(1, maxPages)
                .mapToObj(String::valueOf)
                .toList();
    }

}
