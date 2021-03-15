package com.henryfabio.minecraft.githubupdater.api;

import com.henryfabio.minecraft.githubupdater.api.configuration.UpdaterConfiguration;
import com.henryfabio.minecraft.githubupdater.api.credentials.GithubCredentials;
import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePlugin;
import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePluginHandle;
import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePluginRegistry;
import com.henryfabio.minecraft.githubupdater.api.update.PluginUpdateRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public interface GithubUpdater {

    void registerUpdatablePlugin(@NotNull UpdatablePlugin updatablePlugin);

    void stop();

    @NotNull
    UpdaterConfiguration getConfiguration();

    @NotNull
    GithubCredentials getCredentials();

    @NotNull
    Logger getLogger();

    @NotNull
    UpdatablePluginRegistry getUpdatablePluginRegistry();

    @NotNull
    PluginUpdateRegistry getPluginUpdateRegistry();

    @NotNull
    UpdatablePluginHandle getUpdatablePluginHandle();

}
