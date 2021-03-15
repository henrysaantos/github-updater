package com.henryfabio.minecraft.githubupdater.api.update;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface PluginUpdateRegistry {

    void registerPluginUpdate(@NotNull PluginUpdate pluginUpdate);

    void removePluginUpdate(@NotNull PluginUpdate pluginUpdate);

    @Nullable
    PluginUpdate getPluginUpdateByName(@NotNull String name);

    @NotNull
    Set<PluginUpdate> getPluginUpdates();

}
