package com.dev7ex.multiwarp.localization;

import com.dev7ex.multicommon.api.configuration.ConfigurationProvider;
import com.dev7ex.multicommon.api.configuration.FileConfiguration;
import com.dev7ex.multicommon.api.configuration.JsonConfiguration;
import com.dev7ex.multicommon.api.localization.LocalizationMeta;
import com.dev7ex.multicommon.api.text.TextFormatter;
import com.dev7ex.multicommon.api.util.Files;
import com.dev7ex.multiwarp.api.MultiWarpApi;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationEntry;
import com.dev7ex.multiwarp.api.localization.WarpLocalizationRegistry;
import com.dev7ex.multiwarp.module.DefaultWarpModule;
import lombok.AccessLevel;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
@Getter(AccessLevel.PUBLIC)
public class DefaultWarpLocalizationRegistry extends DefaultWarpModule implements WarpLocalizationRegistry {

    private final Map<Locale, FileConfiguration> translationConfigurations = new HashMap<>();
    private final Locale defaultLocale = new Locale("en", "US");

    public DefaultWarpLocalizationRegistry(@NotNull final MultiWarpApi multiWarpApi) {
        super(multiWarpApi);
    }

    @Override
    public void onEnable() {
        this.multiWarpApi.saveResource("language/de_DE.json", true);
        this.multiWarpApi.saveResource("language/en_US.json", true);

        for (final File file : Files.getFiles(Objects.requireNonNull(super.getMultiWarpApi().getLanguageDirectory()))) {
            if ((file.isFile()) && (file.getName().endsWith(".json"))) {
                final String[] parts = file.getName().replaceAll(".json", "").split("_");
                final Locale locale = new Locale(parts[0], parts[1]);

                try {
                    final FileConfiguration nextConfiguration = ConfigurationProvider.getProvider(JsonConfiguration.class).load(file);
                    this.translationConfigurations.put(locale, nextConfiguration);

                } catch (final IOException exception) {
                    this.multiWarpApi.getLogger().log(Level.SEVERE,
                            String.format("Can't load configuration File for Locale %s", locale),
                            exception);
                }
            }
        }
        this.multiWarpApi.getLogger().info(String.format("Found: [%d] Translation Files",
                this.translationConfigurations.size()));
    }

    @Override
    public void onDisable() {
        this.translationConfigurations.clear();
    }

    @Override
    public void register(@NotNull final Locale locale, @NotNull final File file) {
        try {
            final FileConfiguration configuration = ConfigurationProvider.getProvider(JsonConfiguration.class).load(file);
            this.translationConfigurations.put(locale, configuration);

        } catch (final IOException exception) {
            this.multiWarpApi.getLogger().log(Level.SEVERE,
                    String.format("Can't load configuration File for Locale %s", locale),
                    exception);
        }
    }

    @Override
    public void unregister(@NotNull final Locale locale) {
        this.translationConfigurations.remove(locale);
    }

    @Override
    public void unregister(@NotNull final String localeCode) {
        if (localeCode.isEmpty()) {
            throw new IllegalArgumentException("The localeCode parameter must not be null or empty.");
        }

        final String[] parts = localeCode.split("_");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid localeCode format. Expected format: 'language_COUNTRY', e.g., 'en_US'.");
        }
        this.translationConfigurations.remove(new Locale(parts[0], parts[1]));
    }


    @Override
    public boolean isRegistered(@NotNull final Locale locale) {
        return this.translationConfigurations.containsKey(locale);
    }

    @Override
    public boolean isRegistered(@NotNull final String localeCode) {
        if (localeCode.isEmpty()) {
            throw new IllegalArgumentException("The localeCode parameter must not be null or empty.");
        }

        final String[] parts = localeCode.split("_");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid localeCode format. Expected format: 'language_COUNTRY', e.g., 'en_US'.");
        }
        return this.translationConfigurations.containsKey(new Locale(parts[0], parts[1]));
    }

    @Override
    public Optional<LocalizationMeta> findMeta(@NotNull final Locale locale) {
        return Optional.empty();
    }


    @Override
    public Locale getLocale(@NotNull final CommandSender commandSender) {
        return (commandSender instanceof final Player player) ? player.locale() : this.defaultLocale;
    }

    @Override
    public int resolveMaxPages(@NotNull final Audience audience, @NotNull final String path) {
        final Locale locale = (audience instanceof final Player player) ? player.locale() : this.defaultLocale;
        final FileConfiguration translationConfiguration = this.translationConfigurations.getOrDefault(locale, this.translationConfigurations.get(this.defaultLocale));

        int maxPage = 1;
        final FileConfiguration section = translationConfiguration.getSection(path);
        final Collection<String> keys = (section == null) ? Collections.emptySet() : section.getKeys();

        for (final String key : keys) {
            try {
                final int page = Integer.parseInt(key);
                if (page > maxPage) {
                    maxPage = page;
                }
            } catch (final NumberFormatException ignored) {
            }
        }
        return maxPage;
    }

    @Override
    public int resolveMaxPages(@NotNull Audience audience, @NotNull WarpLocalizationEntry entry) {
        return this.resolveMaxPages(audience, entry.getKey());
    }

    @Override
    public Component resolveComponent(@NotNull Audience audience, @NotNull String path) {
        final Locale locale = (audience instanceof final Player player) ? player.locale() : this.defaultLocale;
        final FileConfiguration config = this.translationConfigurations.getOrDefault(locale, this.translationConfigurations.get(this.defaultLocale));
        String text;
        String hover = null;
        Object clickObject = null;

        try {
            text = config.getString(path + ".text");
            hover = config.getString(path + ".hover");
            clickObject = config.get(path + ".click");

        } catch (final ClassCastException exception) {
            text = config.getString(path);
        }

        if ((text == null) || (text.isEmpty())) {
            return TextFormatter.parse("§cMissing translation for: " + path);
        }

        Component component = TextFormatter.parse(text)
                .replaceText(builder -> builder
                        .matchLiteral("%prefix%")
                        .replacement(this.multiWarpApi.getConfiguration().getPrefix())
                );

        if ((hover != null) && (!hover.isEmpty())) {
            component = component.hoverEvent(HoverEvent.showText(Component.text(hover)));
        }

        if (clickObject != null) {
            if (clickObject instanceof final FileConfiguration fileConfig) {
                final String actionString = fileConfig.getString("action");
                final String clickText = fileConfig.getString("text");

                if (actionString != null && !actionString.isEmpty() && clickText != null && !clickText.isEmpty()) {
                    try {
                        component = component.clickEvent(DefaultWarpLocalizationRegistry.parseClickEvent(actionString, clickText));
                    } catch (final IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return component;
    }

    @Override
    public Component resolveComponent(@NotNull Audience audience, @NotNull String path, @NotNull Consumer<TextReplacementConfig.Builder> replacements) {
        final TextReplacementConfig.Builder builder = TextReplacementConfig.builder();
        replacements.accept(builder);

        final TextReplacementConfig replacementConfig = builder.build();

        return this.resolveComponent(audience, path).replaceText(replacementConfig);
    }

    @Override
    public Component resolveComponent(@NotNull Audience audience, @NotNull WarpLocalizationEntry entry) {
        return this.resolveComponent(audience, entry.getKey());
    }

    @Override
    public Component resolveComponent(@NotNull Audience audience, @NotNull WarpLocalizationEntry entry, @NotNull Consumer<TextReplacementConfig.Builder> replacements) {
        final TextReplacementConfig.Builder builder = TextReplacementConfig.builder();
        replacements.accept(builder);

        final TextReplacementConfig replacementConfig = builder.build();

        return this.resolveComponent(audience, entry).replaceText(replacementConfig);
    }

    @Override
    public List<Component> resolveComponents(@NotNull Audience audience, @NotNull String path) {
        final Locale locale = (audience instanceof final Player player) ? player.locale() : this.defaultLocale;
        final FileConfiguration config = this.translationConfigurations.getOrDefault(locale, this.translationConfigurations.get(this.defaultLocale));
        final List<Component> components = new ArrayList<>();

        final List<?> rawList = config.getList(path);
        if ((rawList == null) || (rawList.isEmpty())) {
            return List.of(Component.text("§cMissing translation for: " + path));
        }

        for (final Object entry : rawList) {
            if (!(entry instanceof Map<?, ?>)) {
                continue;
            }

            final Map<String, Object> map = (Map<String, Object>) entry;
            final String text = (String) map.get("text");
            final String hover = (String) map.get("hover");
            final Object clickObject = map.get("click");

            if (text == null) {
                continue;
            }
            Component component = TextFormatter.parse(text)
                    .replaceText(builder -> builder.matchLiteral("%prefix%")
                            .replacement(this.multiWarpApi.getConfiguration().getPrefix()));

            if ((hover != null) && (!hover.isEmpty())) {
                component = component.hoverEvent(HoverEvent.showText(TextFormatter.parse(hover)));
            }

            if (clickObject != null) {
                if (clickObject instanceof Map<?, ?>) {
                    final Map<String, Object> clickMap = (Map<String, Object>) clickObject;
                    final String actionStr = (String) clickMap.get("action");
                    final String clickText = (String) clickMap.get("text");
                    if (actionStr != null && !actionStr.isEmpty() && clickText != null) {
                        // Umwandeln in einen gültigen Enum-Wert (Großschreibung beachten)
                        component = component.clickEvent(DefaultWarpLocalizationRegistry.parseClickEvent(actionStr, clickText));
                    }

                } else if (clickObject instanceof final String clickText) {
                    if (!clickText.isEmpty()) {
                        component = component.clickEvent(ClickEvent.suggestCommand(clickText));
                    }
                }
            }

            components.add(component);
        }

        return components;
    }

    @Override
    public List<Component> resolveComponents(@NotNull Audience audience, @NotNull String path, @NotNull Map<String, String> replacements) {
        final Locale locale = (audience instanceof final Player player) ? player.locale() : this.defaultLocale;
        final FileConfiguration config = this.translationConfigurations.getOrDefault(locale, this.translationConfigurations.get(this.defaultLocale));
        final List<Component> components = new ArrayList<>();

        final List<?> rawList = config.getList(path);
        if ((rawList == null) || (rawList.isEmpty())) {
            return List.of(Component.text("§cMissing translation for: " + path));
        }

        for (final Object entry : rawList) {
            if (!(entry instanceof Map<?, ?>)) {
                continue;
            }

            final Map<String, Object> map = (Map<String, Object>) entry;
            final String text = (String) map.get("text");
            final String hover = (String) map.get("hover");
            final Object clickObject = map.get("click");

            if (text == null) {
                continue;
            }

            // Basis-Komponente mit Prefix ersetzen
            Component component = TextFormatter.parse(text)
                    .replaceText(builder -> builder.matchLiteral("%prefix%")
                            .replacement(this.multiWarpApi.getConfiguration().getPrefix()));

            // Replacements einfügen
            for (final Map.Entry<String, String> replacement : replacements.entrySet()) {
                final String key = replacement.getKey();
                final String value = replacement.getValue();
                component = component.replaceText(builder -> builder.matchLiteral("%" + key + "%").replacement(value));
            }

            // Hover-Text
            if (hover != null && !hover.isEmpty()) {
                Component hoverComponent = TextFormatter.parse(hover);
                for (final Map.Entry<String, String> replacement : replacements.entrySet()) {
                    hoverComponent = hoverComponent.replaceText(builder -> builder.matchLiteral("%" + replacement.getKey() + "%").replacement(replacement.getValue()));
                }
                component = component.hoverEvent(HoverEvent.showText(hoverComponent));
            }

            // ClickEvent
            if (clickObject != null) {
                if (clickObject instanceof final Map<?, ?> clickMapRaw) {
                    final Map<String, Object> clickMap = (Map<String, Object>) clickMapRaw;
                    final String actionStr = (String) clickMap.get("action");
                    final String clickTextRaw = (String) clickMap.get("text");

                    if (actionStr != null && !actionStr.isEmpty() && clickTextRaw != null) {
                        String clickText = clickTextRaw;

                        // Replacements im ClickText
                        for (final Map.Entry<String, String> replacement : replacements.entrySet()) {
                            clickText = clickText.replace("%" + replacement.getKey() + "%", replacement.getValue());
                        }

                        component = component.clickEvent(DefaultWarpLocalizationRegistry.parseClickEvent(actionStr, clickText));
                    }

                } else if (clickObject instanceof final String clickTextRaw) {
                    if (!clickTextRaw.isEmpty()) {
                        String clickText = clickTextRaw;
                        for (final Map.Entry<String, String> replacement : replacements.entrySet()) {
                            clickText = clickText.replace("%" + replacement.getKey() + "%", replacement.getValue());
                        }
                        component = component.clickEvent(ClickEvent.suggestCommand(clickText));
                    }
                }
            }

            components.add(component);
        }

        return components;
    }

    @Override
    public List<Component> resolveComponents(@NotNull Audience audience, @NotNull WarpLocalizationEntry entry) {
        return this.resolveComponents(audience, entry.getKey());
    }

    @Override
    public List<Component> resolveComponents(@NotNull Audience audience, @NotNull WarpLocalizationEntry localizationEntry, @NotNull Map<String, String> replacements) {
        final Locale locale = (audience instanceof final Player player) ? player.locale() : this.defaultLocale;
        final FileConfiguration config = this.translationConfigurations.getOrDefault(locale, this.translationConfigurations.get(this.defaultLocale));
        final List<Component> components = new ArrayList<>();

        final List<?> rawList = config.getList(localizationEntry.getKey());
        if ((rawList == null) || (rawList.isEmpty())) {
            return List.of(Component.text("§cMissing translation for: " + localizationEntry.getKey()));
        }

        for (final Object entry : rawList) {
            if (!(entry instanceof Map<?, ?>)) {
                continue;
            }

            final Map<String, Object> map = (Map<String, Object>) entry;
            final String text = (String) map.get("text");
            final String hover = (String) map.get("hover");
            final Object clickObject = map.get("click");

            if (text == null) {
                continue;
            }

            // Basis-Komponente mit Prefix ersetzen
            Component component = TextFormatter.parse(text)
                    .replaceText(builder -> builder.matchLiteral("%prefix%")
                            .replacement(this.multiWarpApi.getConfiguration().getPrefix()));

            // Replacements einfügen
            for (final Map.Entry<String, String> replacement : replacements.entrySet()) {
                final String key = replacement.getKey();
                final String value = replacement.getValue();
                component = component.replaceText(builder -> builder.matchLiteral("%" + key + "%").replacement(value));
            }

            // Hover-Text
            if (hover != null && !hover.isEmpty()) {
                Component hoverComponent = TextFormatter.parse(hover);
                for (final Map.Entry<String, String> replacement : replacements.entrySet()) {
                    hoverComponent = hoverComponent.replaceText(builder -> builder.matchLiteral("%" + replacement.getKey() + "%").replacement(replacement.getValue()));
                }
                component = component.hoverEvent(HoverEvent.showText(hoverComponent));
            }

            // ClickEvent
            if (clickObject != null) {
                if (clickObject instanceof final Map<?, ?> clickMapRaw) {
                    final Map<String, Object> clickMap = (Map<String, Object>) clickMapRaw;
                    final String actionStr = (String) clickMap.get("action");
                    final String clickTextRaw = (String) clickMap.get("text");

                    if (actionStr != null && !actionStr.isEmpty() && clickTextRaw != null) {
                        String clickText = clickTextRaw;

                        // Replacements im ClickText
                        for (final Map.Entry<String, String> replacement : replacements.entrySet()) {
                            clickText = clickText.replace("%" + replacement.getKey() + "%", replacement.getValue());
                        }

                        component = component.clickEvent(DefaultWarpLocalizationRegistry.parseClickEvent(actionStr, clickText));
                    }

                } else if (clickObject instanceof final String clickTextRaw) {
                    if (!clickTextRaw.isEmpty()) {
                        String clickText = clickTextRaw;
                        for (final Map.Entry<String, String> replacement : replacements.entrySet()) {
                            clickText = clickText.replace("%" + replacement.getKey() + "%", replacement.getValue());
                        }
                        component = component.clickEvent(ClickEvent.suggestCommand(clickText));
                    }
                }
            }

            components.add(component);
        }

        return components;
    }

    @Override
    public Component createPagination(@NotNull Audience audience, int currentPage, @NotNull String command, @NotNull String path) {
        final int totalPages = this.resolveMaxPages(audience, path);
        final Component pageIndicator = this.resolveComponent(audience, WarpLocalizationEntry.MESSAGE_PAGINATION_INDICATOR);
        final Component previousPage = this.resolveComponent(audience, WarpLocalizationEntry.MESSAGE_PAGINATION_PREVIOUS);
        final Component nextPage = this.resolveComponent(audience, WarpLocalizationEntry.MESSAGE_PAGINATION_NEXT);
        final Component gotoPage = this.resolveComponent(audience, WarpLocalizationEntry.MESSAGE_PAGINATION_GOTO);

        final TextComponent.Builder builder = Component.text();

        builder.append(pageIndicator)
                .append(Component.text(" " + currentPage + "/" + totalPages, NamedTextColor.GREEN))
                .append(Component.space())
                .append(Component.text("[", NamedTextColor.DARK_GRAY));

        // Vorherige Seite
        if (currentPage > 1) {
            builder.append(Component.text("«", NamedTextColor.GRAY)
                            .hoverEvent(HoverEvent.showText(previousPage))
                            .clickEvent(ClickEvent.runCommand(command + (currentPage - 1))))
                    .append(Component.space());
        }

        // Seitenbereich anzeigen
        final int visibleRange = 5;
        int start = Math.max(1, currentPage - 2);
        final int end = Math.min(totalPages, start + visibleRange - 1);
        if (end - start < visibleRange - 1) {
            start = Math.max(1, end - visibleRange + 1);
        }

        if (start > 1) {
            builder.append(Component.text("1", NamedTextColor.WHITE)
                    .decoration(TextDecoration.UNDERLINED, true)
                    .hoverEvent(HoverEvent.showText(Component.text("Zu Seite 1")))
                    .clickEvent(ClickEvent.runCommand(command + "1")));
            builder.append(Component.text(" ... ", NamedTextColor.DARK_GRAY));
        }

        // Aktuelle Seitenzahl und die auswählbaren Seiten
        for (int i = start; i <= end; i++) {
            if (i != start && i != 1) {
                builder.append(Component.text(" | ", NamedTextColor.DARK_GRAY));
            }

            final int finalI = i;
            if (i == currentPage) {
                builder.append(Component.text(String.valueOf(i), NamedTextColor.GREEN));
                continue;
            }

            builder.append(Component.text(String.valueOf(i), NamedTextColor.WHITE)
                    .decoration(TextDecoration.UNDERLINED, true)
                    .hoverEvent(HoverEvent.showText(gotoPage.replaceText(b -> b
                            .match("%page_index%")
                            .replacement(String.valueOf(finalI)))))
                    .clickEvent(ClickEvent.runCommand(command + i)));


        }

        // Letzte Seite
        if (end < totalPages) {
            builder.append(Component.text(" ... ", NamedTextColor.DARK_GRAY));
            builder.append(Component.text(String.valueOf(totalPages), NamedTextColor.WHITE)
                    .decoration(TextDecoration.UNDERLINED, true)
                    .clickEvent(ClickEvent.runCommand(command + totalPages)));
        }

        // Nächste Seite
        if (currentPage < totalPages) {
            builder.append(Component.space())
                    .append(Component.text("»", NamedTextColor.GRAY)
                            .hoverEvent(HoverEvent.showText(nextPage))
                            .clickEvent(ClickEvent.runCommand(command + (currentPage + 1))));
        }

        builder.append(Component.text("]", NamedTextColor.DARK_GRAY));
        return builder.build();
    }

    @Override
    public Component createPagination(@NotNull Audience audience, int currentPage, @NotNull String command, @NotNull WarpLocalizationEntry entry) {
        return this.createPagination(audience, currentPage, command, entry.getKey());
    }

    @Override
    public Component createHeader(@NotNull Audience audience, @NotNull Component replacement) {
        final List<Component> components = this.resolveComponents(audience, WarpLocalizationEntry.MESSAGE_LAYOUT_HEADER)
                .stream()
                .map(component -> component.replaceText(builder -> builder
                        .match("%placeholder%")
                        .replacement(replacement)
                ))
                .toList();
        return Component.join(JoinConfiguration.separator(Component.text("\n")), components);
    }

    @Override
    public Component createFooter(@NotNull Audience audience, @NotNull Component replacement) {
        final List<Component> components = this.resolveComponents(audience, WarpLocalizationEntry.MESSAGE_LAYOUT_FOOTER)
                .stream()
                .map(component -> component.replaceText(builder -> builder
                        .match("%placeholder%")
                        .replacement(replacement)
                ))
                .toList();
        return Component.join(JoinConfiguration.separator(Component.text("\n")), components);
    }

    @Override
    public @NotNull DateTimeFormatter resolveDateTimeFormatter(@NotNull Audience audience) {
        final Locale locale = (audience instanceof final Player player) ? player.locale() : this.defaultLocale;
        final FileConfiguration config = this.translationConfigurations.getOrDefault(locale, this.translationConfigurations.get(this.defaultLocale));

        return DateTimeFormatter.ofPattern(config.getString(WarpLocalizationEntry.FORMAT_DATE_TIME.getKey()), locale);
    }

    private static ClickEvent parseClickEvent(@NotNull final String action, @NotNull final String value) {
        return switch (action.toUpperCase(Locale.ROOT)) {
            case "RUN_COMMAND" -> ClickEvent.runCommand(value);
            case "SUGGEST_COMMAND" -> ClickEvent.suggestCommand(value);
            case "OPEN_URL" -> ClickEvent.openUrl(value);
            case "COPY_TO_CLIPBOARD" -> ClickEvent.copyToClipboard(value);
            case "CHANGE_PAGE" -> ClickEvent.changePage(Integer.parseInt(value));
            default -> throw new IllegalArgumentException("Unsupported click action: " + action);
        };
    }

}
