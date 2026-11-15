package com.dev7ex.multiwarp.warp;

import com.dev7ex.multiwarp.api.warp.Warp;
import com.dev7ex.multiwarp.api.warp.WarpFactory;
import com.dev7ex.multiwarp.api.warp.WarpLocation;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
public class DefaultWarpFactory implements WarpFactory {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{1,32}$");

    @Override
    public @NotNull Warp createWarp(@NotNull final String name, @NotNull final WarpLocation location) {
        return this.createWarp(name, name, location, false);
    }

    @Override
    public @NotNull Warp createWarp(@NotNull final String name, @NotNull final String displayName, @NotNull final WarpLocation location, final boolean locked) {
        this.validateName(name);

        if (displayName.isBlank()) {
            throw new IllegalArgumentException(String.format("Invalid display name: %s", displayName));
        }
        return DefaultWarp.builder()
                .setName(name)
                .setDisplayName(displayName)
                .setLocation(location)
                .setLocked(locked)
                .build();
    }

    private void validateName(@NotNull final String name) {
        if (!DefaultWarpFactory.NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException(String.format("Invalid warp name: %s (allowed: a-z, A-Z, 0-9, _ and -, 1-32 characters)", name));
        }
    }

}
