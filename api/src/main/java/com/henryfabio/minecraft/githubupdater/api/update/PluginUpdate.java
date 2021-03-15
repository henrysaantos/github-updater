package com.henryfabio.minecraft.githubupdater.api.update;

import com.henryfabio.minecraft.githubupdater.api.github.asset.Asset;
import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface PluginUpdate {

    void replacePluginFile();

    @NotNull
    UpdatablePlugin getPlugin();

    @NotNull
    Asset getPluginAsset();

    @NotNull
    String getVersion();

    @NotNull
    File getDownloadFile();

}
