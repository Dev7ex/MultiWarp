package com.dev7ex.multiwarp.api.warp;

import com.dev7ex.multicommon.api.configuration.ConfigurationBase;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
public interface WarpRepository extends ConfigurationBase {

    void save(@NotNull final Warp warp);

    void rename(@NotNull final String oldName, @NotNull final Warp warp);

    void delete(@NotNull final String name);

    boolean contains(@NotNull final String name);

    @NotNull
    Optional<Warp> load(@NotNull final String name);

    @NotNull
    Collection<Warp> loadAll();

    void sanitize();

}
