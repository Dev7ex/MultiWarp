package com.dev7ex.multiwarp.command.warp;

import com.dev7ex.common.bukkit.command.BukkitCommand;
import com.dev7ex.common.bukkit.command.BukkitCommandProperties;
import com.dev7ex.common.bukkit.command.completer.BukkitTabCompleter;
import com.dev7ex.common.bukkit.plugin.BukkitPlugin;
import com.dev7ex.common.util.Numbers;
import com.dev7ex.multiwarp.MultiWarpPlugin;
import com.dev7ex.multiwarp.api.bukkit.event.warp.WarpFlagChangeEvent;
import com.dev7ex.multiwarp.api.bukkit.warp.BukkitWarp;
import com.dev7ex.multiwarp.api.warp.WarpFlag;
import com.dev7ex.multiwarp.api.warp.WarpProperty;
import com.dev7ex.multiwarp.translation.DefaultTranslationProvider;
import com.dev7ex.multiwarp.warp.DefaultWarpConfiguration;
import com.dev7ex.multiwarp.warp.DefaultWarpProvider;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Dev7ex
 * @since 16.08.2024
 */
@BukkitCommandProperties(name = "flag", permission = "multiwarp.command.warp.flag")
public class FlagCommand extends BukkitCommand implements BukkitTabCompleter {

    public FlagCommand(@NotNull final BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        final DefaultTranslationProvider translationProvider = MultiWarpPlugin.getInstance().getTranslationProvider();

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "general.no-console-command")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%command_name%", super.getName()));
            return;
        }

        if (arguments.length != 4) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.flag.usage")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix()));
            return;
        }
        final DefaultWarpProvider warpProvider = MultiWarpPlugin.getInstance().getWarpProvider();

        if (!warpProvider.isRegistered(arguments[1])) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "general.warp.not-exists")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%warp_name%", arguments[1]));
            return;
        }
        final DefaultWarpConfiguration warpConfiguration = MultiWarpPlugin.getInstance().getWarpConfiguration();
        final BukkitWarp warp = warpProvider.getWarp(arguments[1]).orElseThrow();
        final Optional<WarpFlag> flagOptional = WarpFlag.fromString(arguments[2].toUpperCase());

        if (flagOptional.isEmpty()) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.flag.not-existing")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%flag_name%", arguments[2]));
            return;
        }
        final WarpFlag flag = flagOptional.get();

        if ((!flag.getValues().isEmpty()) && (!flag.getValues().contains(arguments[3]))) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.flag.invalid-value")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%value%", arguments[3])
                    .replaceAll("%flag_name%", flag.toString()));
            return;
        }

        if ((flag.getDataType() == Boolean.class) && (!Boolean.parseBoolean(arguments[3]))) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.flag.invalid-value")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%value%", arguments[3])
                    .replaceAll("%flag_name%", flag.toString()));
            return;
        }

        if ((flag.getDataType() == Double.class) && (!Numbers.isDouble(arguments[3]))) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.flag.invalid-value")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%value%", arguments[3])
                    .replaceAll("%flag_name%", flag.toString()));
            return;
        }

        if ((flag == WarpFlag.PERMISSION) && (arguments[3].equalsIgnoreCase("null"))) {
            commandSender.sendMessage(translationProvider.getMessage(commandSender, "general.warp.permission-removed")
                    .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                    .replaceAll("%warp_name%", arguments[1]));
            warp.updateFlag(WarpFlag.PERMISSION, "");
            warpConfiguration.write(warp, WarpProperty.PERMISSION, "");
            return;
        }

        final WarpFlagChangeEvent event = new WarpFlagChangeEvent(warp, flag, arguments[3]);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        warp.updateFlag(flag, arguments[3]);
        warpConfiguration.write(warp, WarpFlag.toProperty(flag), arguments[3]);
        commandSender.sendMessage(translationProvider.getMessage(commandSender, "commands.warp.flag.successfully-set")
                .replaceAll("%prefix%", super.getConfiguration().getPrefix())
                .replaceAll("%flag_name%", flag.toString())
                .replaceAll("%value%", arguments[3])
                .replaceAll("%warp_name%", arguments[1]));
    }

    @Override
    public List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final String[] arguments) {
        if (!(commandSender instanceof Player)) {
            return Collections.emptyList();
        }

        if (arguments.length == 2) {
            return new ArrayList<>(MultiWarpPlugin.getInstance().getWarpProvider().getWarps().keySet());
        }

        if (arguments.length == 3) {
            return WarpFlag.toStringList();
        }
        final Optional<WarpFlag> flagOptional = WarpFlag.fromString(arguments[2].toUpperCase());

        if (flagOptional.isEmpty()) {
            return Collections.emptyList();
        }
        final WarpFlag warpFlag = flagOptional.get();
        final Player player = (Player) commandSender;

        switch (warpFlag) {
            case LOCKED:
                return warpFlag.getValues();

            case PERMISSION:
                return List.of("multiwarp.access." + arguments[1], "null");

            case WORLD_NAME:
                return Bukkit.getWorlds().stream().map(World::getName).toList();

            case X:
                return Collections.singletonList(String.valueOf(player.getLocation().getX()));

            case Y:
                return Collections.singletonList(String.valueOf(player.getLocation().getY()));

            case Z:
                return Collections.singletonList(String.valueOf(player.getLocation().getZ()));

            case PITCH:
                return Collections.singletonList(String.valueOf(player.getLocation().getPitch()));

            case YAW:
                return Collections.singletonList(String.valueOf(player.getLocation().getYaw()));

            default:
                return Collections.emptyList();
        }
    }

}