package com.dev7ex.multiwarp.module;

import com.dev7ex.multiwarp.api.MultiWarpApi;
import com.dev7ex.multiwarp.api.module.WarpModule;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
@Getter(AccessLevel.PUBLIC)
public abstract class DefaultWarpModule implements WarpModule {

    protected final MultiWarpApi multiWarpApi;

    protected DefaultWarpModule(@NotNull final MultiWarpApi multiWarpApi) {
        this.multiWarpApi = multiWarpApi;
    }

}
