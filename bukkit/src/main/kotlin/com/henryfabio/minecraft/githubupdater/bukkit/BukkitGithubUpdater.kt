package com.henryfabio.minecraft.githubupdater.bukkit

import com.henryfabio.minecraft.githubupdater.api.configuration.UpdaterConfiguration
import com.henryfabio.minecraft.githubupdater.api.credentials.GithubCredentials
import com.henryfabio.minecraft.githubupdater.common.AbstractGithubUpdater
import com.henryfabio.minecraft.githubupdater.common.scheduler.UpdaterCheckerRunnable
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

class BukkitGithubUpdater(
    plugin: Plugin,
    configuration: UpdaterConfiguration,
    credentials: GithubCredentials? = null
) : AbstractGithubUpdater(configuration, credentials) {

    init {
        Bukkit.getScheduler().runTaskTimerAsynchronously(
            plugin,
            UpdaterCheckerRunnable(
                this.logger,
                this.credentials,
                this.updatablePluginRegistry,
                this.pluginUpdateRegistry,
                this.updatablePluginHandle
            ),
            0,
            configuration.updaterCheckerPeriod()
        )
    }

}