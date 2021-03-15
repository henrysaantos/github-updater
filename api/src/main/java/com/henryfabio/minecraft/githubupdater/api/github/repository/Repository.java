package com.henryfabio.minecraft.githubupdater.api.github.repository;

import com.henryfabio.minecraft.githubupdater.api.github.release.Release;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kohsuke.github.GHRepository;

import java.util.List;
import java.util.function.Predicate;

public interface Repository {

    void refreshReleases();

    @Nullable
    Release getLatestRelease(@NotNull Predicate<Release> filter);

    @NotNull
    String getName();

    @NotNull
    GHRepository getGithubRepository();

    @NotNull
    List<Release> getReleases();

}
