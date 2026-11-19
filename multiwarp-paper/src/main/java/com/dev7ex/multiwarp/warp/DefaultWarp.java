
package com.dev7ex.multiwarp.warp;

import com.dev7ex.multiwarp.api.warp.Warp;
import com.dev7ex.multiwarp.api.warp.WarpLocation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Default implementation of the {@link Warp} interface.
 *
 * @author Dev7ex
 * @since 15.11.2026
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder(toBuilder = true, setterPrefix = "set")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class DefaultWarp implements Warp {

    @NonNull
    @NotNull
    private String name;

    @NonNull
    @NotNull
    private String displayName;

    @NonNull
    @NotNull
    private String creatorName;

    @NonNull
    @NotNull
    private WarpLocation location;

    @Builder.Default
    @NonNull
    @NotNull
    private TextColor color = TextColor.color(0xFFFF55);

    @Builder.Default
    @NonNull
    @NotNull
    private Material icon = Material.ENDER_PEARL;

    @Builder.Default
    private long registrationTimestamp = System.currentTimeMillis();

    private boolean locked;

    @Override
    @EqualsAndHashCode.Include
    @NotNull
    public String getCanonicalName() {
        return this.name.toLowerCase(Locale.ROOT);
    }

}
