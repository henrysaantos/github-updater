package com.henryfabio.minecraft.githubupdater.api.plugin;

import com.henryfabio.minecraft.githubupdater.api.exceptions.plugin.UnknownPluginRepositoryException;
import com.henryfabio.minecraft.githubupdater.api.update.PluginUpdate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UpdatablePluginHandle {

    @Nullable
    PluginUpdate handle(@NotNull UpdatablePlugin updatablePlugin) throws UnknownPluginRepositoryException;

}
