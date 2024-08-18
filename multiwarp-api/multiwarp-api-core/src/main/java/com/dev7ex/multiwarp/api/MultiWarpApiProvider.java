package com.dev7ex.multiwarp.api;

import org.jetbrains.annotations.NotNull;

/**
 * The {@code MultiWarpApiProvider} class serves as a singleton-like provider
 * for the {@link MultiWarpApi} instance. It allows registering, unregistering,
 * and retrieving the API instance throughout the application.
 *
 * <p>This class is intended to manage the lifecycle of the {@code MultiWarpApi}
 * instance, ensuring that it is available globally within the application.
 *
 * <p><strong>Usage:</strong>
 * <ul>
 *   <li>Use {@link #registerApi(MultiWarpApi)} to register an API instance.</li>
 *   <li>Use {@link #unregisterApi()} to unregister the API instance.</li>
 *   <li>Use {@link #getMultiWarpApi()} to retrieve the currently registered API instance.</li>
 * </ul>
 *
 * @author Dev7ex
 * @since 15.08.2024
 */
public class MultiWarpApiProvider {

    private static MultiWarpApi multiWarpApi;

    /**
     * Registers an instance of {@link MultiWarpApi}.
     *
     * @param multiWarpApi The instance to be registered. Must not be null.
     */
    public static void registerApi(@NotNull final MultiWarpApi multiWarpApi) {
        MultiWarpApiProvider.multiWarpApi = multiWarpApi;
    }

    /**
     * Unregisters the currently registered {@link MultiWarpApi} instance.
     * After this method is called, {@link #getMultiWarpApi()} will return null.
     */
    public static void unregisterApi() {
        MultiWarpApiProvider.multiWarpApi = null;
    }

    /**
     * Retrieves the currently registered {@link MultiWarpApi} instance.
     *
     * @return The currently registered {@link MultiWarpApi} instance, or null if none is registered.
     */
    public static MultiWarpApi getMultiWarpApi() {
        return MultiWarpApiProvider.multiWarpApi;
    }
}
