package com.dev7ex.multiwarp.api.localization;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 15.11.2026
 */
@Getter(AccessLevel.PUBLIC)
public enum WarpLocalizationEntry {

    COMMAND_WARP_CREATE_USAGE,
    COMMAND_WARP_CREATE_STARTING,
    COMMAND_WARP_CREATE_CANCELLED,
    COMMAND_WARP_CREATE_FINISHING,

    COMMAND_WARP_DELETE_USAGE,
    COMMAND_WARP_DELETE_STARTING,
    COMMAND_WARP_DELETE_CANCELLED,
    COMMAND_WARP_DELETE_FINISHING,

    COMMAND_WARP_HELP,
    COMMAND_WARP_HELP_USAGE,

    COMMAND_WARP_INFO_USAGE,
    COMMAND_WARP_INFO_MESSAGE,

    COMMAND_WARP_LIST_USAGE,
    COMMAND_WARP_LIST_MESSAGE,

    COMMAND_WARP_LOCK_USAGE,
    COMMAND_WARP_LOCK_LOCKED,
    COMMAND_WARP_LOCK_UNLOCKED,

    COMMAND_WARP_TELEPORT_USAGE_ADMIN,
    COMMAND_WARP_TELEPORT_USAGE_USER,


    COMMAND_WARP_VERSION_HELP,
    COMMAND_WARP_VERSION_USAGE,

    COMMAND_WARP_VERSION_CHECK_USAGE,
    COMMAND_WARP_VERSION_HELP_USAGE,
    COMMAND_WARP_VERSION_INFO_USAGE,
    COMMAND_WARP_VERSION_INFO_MESSAGE,


    COMMAND_WARP_VERSION_REPORT_USAGE,
    COMMAND_WARP_VERSION_REPORT_STARTING,
    COMMAND_WARP_VERSION_REPORT_FAILED,
    COMMAND_WARP_VERSION_REPORT_FINISHING,

    FORMAT_DATE("format.date"),
    FORMAT_DATE_TIME("format.dateTime"),
    FORMAT_TIME("format.time"),

    MESSAGE_NO_PERMISSION("message.no-permission"),
    MESSAGE_NO_CONSOLE_COMMAND("message.no-console-command"),
    MESSAGE_NO_PLAYER_COMMAND("message.no-player-command"),
    MESSAGE_NO_PLAYER_FOUND("message.no-player-found"),

    MESSAGE_LAYOUT_HEADER("message.layout.header"),
    MESSAGE_LAYOUT_FOOTER("message.layout.header"),

    MESSAGE_WARP_CREATE_STARTING(),
    MESSAGE_WARP_CREATE_CANCELLED,
    MESSAGE_WARP_CREATE_FINISHING,

    MESSAGE_WARP_DELETE_STARTING(),
    MESSAGE_WARP_DELETE_CANCELLED,
    MESSAGE_WARP_DELETE_FINISHING,

    MESSAGE_PAGINATION_INDICATOR("message.pagination.indicator"),
    MESSAGE_PAGINATION_PREVIOUS("message.pagination.previous"),
    MESSAGE_PAGINATION_NEXT("message.pagination.next"),
    MESSAGE_PAGINATION_GOTO("message.pagination.goto"),
    MESSAGE_PAGINATION_NOT_FOUND("message.pagination.not-found"),
    MESSAGE_PAGINATION_OUT_OF_RANGE("message.pagination.out-of-range"),

    MESSAGE_VALIDATION_INVALID_NUMBER("message.validation.invalid-number"),
    MESSAGE_VALIDATION_INVALID_BOOLEAN("message.validation.invalid-boolean"),
    MESSAGE_VALIDATION_INVALID_SELECTOR("message.validation.invalid-selector"),

    MESSAGE_WARP_ALREADY_EXISTS("message.warp.already-exists"),
    MESSAGE_WARP_NOT_ACTIVE,
    MESSAGE_WARP_NOT_EXISTS("message.warp.not-exists");

    private final String key;

    WarpLocalizationEntry() {
        this.key = this.name().toLowerCase().replace("_", ".");
    }

    WarpLocalizationEntry(@NotNull final String key) {
        this.key = key;
    }

    public String getKeyForPage(final int page) {
        return this.key + "." + page;
    }

}
