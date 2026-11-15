package com.dev7ex.multiwarp.api;

import org.jetbrains.annotations.NotNull;

/**
 * Static access point for the {@link MultiWarpApi}.
 *
 * @author Dev7ex
 * @since 20.12.2022
 */
public final class MultiWarpApiProvider {

    private static volatile MultiWarpApi multiWarpApi;

    private MultiWarpApiProvider() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static synchronized void register(@NotNull final MultiWarpApi api) {
        if (multiWarpApi != null) {
            throw new IllegalStateException("MultiWorldApi is already registered");
        }
        multiWarpApi = api;
    }

    public static synchronized void unregister() {
        multiWarpApi = null;
    }

    @NotNull
    public static MultiWarpApi getMultiWorldApi() {
        final MultiWarpApi api = multiWarpApi;
        if (api == null) {
            throw new IllegalStateException("MultiWorldApi is not registered");
        }
        return api;
    }

    public static boolean isRegistered() {
        return multiWarpApi != null;
    }

}
