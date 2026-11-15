package com.dev7ex.multiwarp.listener.plugin;

import com.dev7ex.multiwarp.api.MultiWarpApi;
import net.kyori.adventure.text.Component;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class MultiWarpFileChangeListener implements FileAlterationListener {

    private final MultiWarpApi multiWarpApi;

    public MultiWarpFileChangeListener(@NotNull final MultiWarpApi multiWarpApi) {
        this.multiWarpApi = multiWarpApi;
    }

    @Override
    public void onDirectoryChange(final File file) {

    }

    @Override
    public void onDirectoryCreate(final File file) {

    }

    @Override
    public void onDirectoryDelete(final File file) {

    }

    @Override
    public void onFileChange(final File file) {

    }

    @Override
    public void onFileCreate(final File file) {

    }

    @Override
    public void onFileDelete(final File file) {

    }

    @Override
    public void onStart(final FileAlterationObserver fileAlterationObserver) {

    }

    @Override
    public void onStop(final FileAlterationObserver fileAlterationObserver) {

    }

}
