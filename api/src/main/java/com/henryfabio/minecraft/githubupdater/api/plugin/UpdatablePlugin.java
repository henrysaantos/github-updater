package com.henryfabio.minecraft.githubupdater.api.plugin;

import com.henryfabio.minecraft.githubupdater.api.github.repository.Repository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public interface UpdatablePlugin {

    @Nullable
    Repository findRepository();

    @NotNull
    String getName();

    @NotNull
    String getVersion();

    @NotNull
    String getRepositoryName();

    @NotNull
    File getPluginFile();

}
