package com.henryfabio.minecraft.githubupdater.api.github.release;

import com.henryfabio.minecraft.githubupdater.api.github.asset.Asset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kohsuke.github.GHRelease;

import java.util.Date;

public interface Release extends Comparable<Release> {

    @NotNull
    String getName();

    @NotNull
    String getTag();

    @NotNull
    Date getCreationDate();

    boolean isPreRelease();

    @NotNull
    GHRelease getGithubRelease();

    @Nullable
    Asset getPluginAsset();

}
