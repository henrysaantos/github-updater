package com.henryfabio.minecraft.githubupdater.api.plugin;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface UpdatablePluginRegistry {

    @Nullable
    UpdatablePlugin getUpdatablePluginByName(@NotNull String name);

    void registerUpdatablePlugin(@NotNull UpdatablePlugin updatablePlugin);

    @NotNull
    Map<String, UpdatablePlugin> getUpdatablePlugins();

}
