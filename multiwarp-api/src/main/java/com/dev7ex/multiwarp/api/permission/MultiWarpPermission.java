package com.dev7ex.multiwarp.api.permission;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
public enum MultiWarpPermission {

    // Root
    COMMAND_WARP("multiwarp.command.warp", PermissionDefault.OP);

    private final String node;
    private final PermissionDefault defaultValue;

    MultiWarpPermission(@NotNull final String node, @NotNull final PermissionDefault defaultValue) {
        this.node = node;
        this.defaultValue = defaultValue;
    }

    public Permission asBukkit() {
        return new Permission(this.node, this.defaultValue);
    }

}
