package com.henryfabio.minecraft.githubupdater.api.configuration;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;

@Accessors(fluent = true)
@Builder
@Data
public final class UpdaterConfiguration {

    public static final UpdaterConfiguration DEFAULT = UpdaterConfiguration.builder()
            .allowPreReleases(false)
            .downloadsDirectory(new File("plugins", "PluginUpdates"))
            .updaterCheckerPeriod(20 * 60 * 15)
            .build();

    private final boolean allowPreReleases;
    private final File downloadsDirectory;
    private final long updaterCheckerPeriod;

}
