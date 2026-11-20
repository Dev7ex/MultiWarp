
package com.dev7ex.multiwarp.api.warp;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Represents a warp point with its location, appearance,
 * creator information and access settings.
 *
 * @author Dev7ex
 * @since 15.11.2026
 */
public interface Warp extends Comparable<Warp>, Cloneable {

    @NotNull
    String getName();

    void setName(@NotNull final String name);

    @NotNull
    String getDisplayName();

    void setDisplayName(@NotNull final String displayName);

    @NotNull
    TextColor getColor();

    void setColor(@NotNull final TextColor color);

    @NotNull
    Material getIcon();

    void setIcon(@NotNull final Material icon);

    @NotNull
    String getCreatorName();

    void setCreatorName(@NotNull final String creatorName);

    long getRegistrationTimestamp();

    void setRegistrationTimestamp(final long registrationTimestamp);

    boolean isLocked();

    void setLocked(final boolean locked);

    @NotNull
    WarpLocation getLocation();

    void setLocation(@NotNull final WarpLocation location);

    @NotNull
    default String getCanonicalName() {
        return this.getName().toLowerCase(Locale.ROOT);
    }

    @NotNull
    default Component getColoredName() {
        return Component.text(this.getName(), this.getColor());
    }

    @NotNull
    default Component getColoredDisplayName() {
        return Component.text(this.getDisplayName(), this.getColor());
    }

    @Override
    default int compareTo(@NotNull final Warp warp) {
        return this.getCanonicalName()
                .compareTo(warp.getCanonicalName());
    }

}
