
package com.dev7ex.multiwarp.warp;

import com.dev7ex.multiwarp.api.warp.Warp;
import com.dev7ex.multiwarp.api.warp.WarpLocation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder(toBuilder = true, setterPrefix = "set")
public class DefaultWarp implements Warp {

    @NotNull
    private String name;

    @NotNull
    private String displayName;

    @NotNull
    private WarpLocation location;

    private boolean locked;

}
