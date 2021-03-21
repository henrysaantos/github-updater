package com.henryfabio.minecraft.githubupdater.common.scheduler

import com.henryfabio.minecraft.githubupdater.api.credentials.GithubCredentials
import com.henryfabio.minecraft.githubupdater.api.exceptions.plugin.UpdatablePluginException
import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePluginHandle
import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePluginRegistry
import com.henryfabio.minecraft.githubupdater.api.update.PluginUpdateRegistry
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.logging.Logger

class UpdaterCheckerRunnable(
    private val logger: Logger,
    private val credentials: GithubCredentials?,
    private val updatablePluginRegistry: UpdatablePluginRegistry,
    private val pluginUpdateRegistry: PluginUpdateRegistry,
    private val updatablePluginHandle: UpdatablePluginHandle
) : Runnable {

    override fun run() {
        for (plugin in updatablePluginRegistry.updatablePlugins.values) {
            try {
                val pluginUpdate = updatablePluginHandle.handle(plugin) ?: continue

                val currentPluginUpdate = pluginUpdateRegistry.getPluginUpdateByName(plugin.name)
                if (currentPluginUpdate != null) {
                    if (currentPluginUpdate.version == pluginUpdate.version) continue
                    pluginUpdateRegistry.removePluginUpdate(pluginUpdate)
                }

                logger.info("Plugin \"" + plugin.name + "\" is outdated! Downloading the latest version: " + pluginUpdate.version)

                pluginUpdate.pluginAsset.download(
                    pluginUpdate.downloadFile,
                    credentials?.accessToken()
                ).get(5, TimeUnit.MINUTES)

                pluginUpdateRegistry.registerPluginUpdate(pluginUpdate)
                logger.info(
                    "Download the latest version of plugin \"" + plugin.name + "\"! " +
                            "The update will be completed when the server closes!"
                )
            } catch (exception: UpdatablePluginException) {
                logger.severe(exception.message)
            } catch (exception: TimeoutException) {
                logger.severe("Downloading the plugin asset \"" + plugin.name + "\" took too long to complete!")
            }
        }
    }

}