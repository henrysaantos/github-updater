package com.henryfabio.minecraft.githubupdater.common

import com.henryfabio.minecraft.githubupdater.api.GithubUpdater
import com.henryfabio.minecraft.githubupdater.api.configuration.UpdaterConfiguration
import com.henryfabio.minecraft.githubupdater.api.credentials.GithubCredentials
import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePlugin
import com.henryfabio.minecraft.githubupdater.common.github.GithubClient
import com.henryfabio.minecraft.githubupdater.common.impl.plugin.UpdatablePluginHandleImpl
import com.henryfabio.minecraft.githubupdater.common.impl.plugin.UpdatablePluginRegistryImpl
import com.henryfabio.minecraft.githubupdater.common.impl.update.PluginUpdateRegistryImpl
import java.util.logging.Logger

open class AbstractGithubUpdater(
    private val configuration: UpdaterConfiguration,
    private val credentials: GithubCredentials?
) : GithubUpdater {

    private val logger: Logger = Logger.getLogger("github-updater")

    private val updatablePluginRegistry = UpdatablePluginRegistryImpl()
    private val pluginUpdateRegistry = PluginUpdateRegistryImpl()

    private val updatablePluginHandle = UpdatablePluginHandleImpl(configuration)

    init {
        GithubClient.connect(credentials)

        val downloadsDirectory = configuration.downloadsDirectory()
        downloadsDirectory.mkdirs()
    }

    override fun registerUpdatablePlugin(updatablePlugin: UpdatablePlugin) {
        updatablePluginRegistry.registerUpdatablePlugin(updatablePlugin)
    }

    override fun stop() {
        pluginUpdateRegistry.pluginUpdates.forEach {
            it.replacePluginFile()
        }

        pluginUpdateRegistry.pluginUpdates.clear()
    }

    override fun getConfiguration() = configuration

    override fun getCredentials() = credentials

    override fun getLogger() = logger

    override fun getUpdatablePluginRegistry() = updatablePluginRegistry

    override fun getPluginUpdateRegistry() = pluginUpdateRegistry

    override fun getUpdatablePluginHandle() = updatablePluginHandle

}