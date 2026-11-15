package com.dev7ex.multiwarp.api.localization;

import com.dev7ex.multicommon.api.localization.LocalizationRegistry;
import com.dev7ex.multiwarp.api.module.WarpModule;
import com.dev7ex.multiwarp.api.module.WarpModuleManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
public interface WarpLocalizationRegistry extends LocalizationRegistry, WarpModule {


    int resolveMaxPages(@NotNull final Audience audience,
                        @NotNull final String path);

    int resolveMaxPages(@NotNull final Audience audience,
                        @NotNull final WarpLocalizationEntry entry);

    Component resolveComponent(@NotNull final Audience audience,
                               @NotNull final String path);

    Component resolveComponent(@NotNull final Audience audience,
                               @NotNull final String path,
                               @NotNull final Consumer<TextReplacementConfig.Builder> replacements);

    Component resolveComponent(@NotNull final Audience audience,
                               @NotNull final WarpLocalizationEntry entry);

    Component resolveComponent(@NotNull final Audience audience,
                               @NotNull final WarpLocalizationEntry entry,
                               @NotNull final Consumer<TextReplacementConfig.Builder> replacements);

    List<Component> resolveComponents(@NotNull final Audience audience,
                                      @NotNull final String path);

    List<Component> resolveComponents(@NotNull final Audience audience,
                                      @NotNull final String path,
                                      @NotNull final Map<String, String> replacements);

    List<Component> resolveComponents(@NotNull final Audience audience,
                                      @NotNull final WarpLocalizationEntry entry);

    List<Component> resolveComponents(@NotNull final Audience audience,
                                      @NotNull final WarpLocalizationEntry entry,
                                      @NotNull final Map<String, String> replacements);

    Component createPagination(@NotNull final Audience audience,
                               final int currentPage,
                               @NotNull final String command,
                               @NotNull final String path);

    Component createPagination(@NotNull final Audience audience,
                               final int currentPage,
                               @NotNull final String command,
                               @NotNull final WarpLocalizationEntry entry);

    Component createHeader(@NotNull final Audience audience,
                           @NotNull final Component replacement);

    Component createFooter(@NotNull final Audience audience,
                           @NotNull final Component replacement);

    @NotNull
    DateTimeFormatter resolveDateTimeFormatter(@NotNull final Audience audience);

}
