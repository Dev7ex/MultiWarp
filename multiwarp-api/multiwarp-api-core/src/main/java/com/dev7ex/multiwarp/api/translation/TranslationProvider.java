package com.dev7ex.multiwarp.api.translation;

import com.dev7ex.common.io.file.configuration.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Interface for managing translations.
 * Provides methods to register, unregister, and retrieve translations based on locale and entity.
 *
 * @param <E> The type of the entity for which translations are managed.
 * @since 16.08.2024
 */
public interface TranslationProvider<E> {

    /**
     * Retrieves the translation configurations for all registered locales.
     *
     * @return A map where the key is a Locale and the value is the corresponding FileConfiguration.
     */
    Map<Locale, FileConfiguration> getTranslationConfigurations();

    /**
     * Registers a new translation configuration file for a specific locale.
     *
     * @param locale The locale for which the file should be registered.
     * @param file The file containing the translation configuration.
     */
    void register(@NotNull final Locale locale, @NotNull final File file);

    /**
     * Unregisters the translation configuration for a specific locale.
     *
     * @param locale The locale for which the configuration should be unregistered.
     */
    void unregister(@NotNull final Locale locale);

    /**
     * Retrieves the default locale used when no specific locale is provided.
     *
     * @return The default locale.
     */
    Locale getDefaultLocale();

    /**
     * Retrieves a translated message for a given entity and path.
     *
     * @param entity The entity for which the message is retrieved.
     * @param path The path in the translation configuration to look up.
     * @return The translated message.
     */
    String getMessage(@NotNull final E entity, @NotNull final String path);

    /**
     * Retrieves a list of translated messages for a given entity and path.
     *
     * @param entity The entity for which the message list is retrieved.
     * @param path The path in the translation configuration to look up.
     * @return A list of translated messages.
     */
    List<String> getMessageList(@NotNull final E entity, @NotNull final String path);

}
