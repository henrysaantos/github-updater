package com.henryfabio.minecraft.githubupdater.api.github.asset;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public interface Asset {

    @NotNull
    CompletableFuture<Void> download(@NotNull File outputFile, @NotNull String accessToken);

    @NotNull
    URL getDownloadUrl();

}
