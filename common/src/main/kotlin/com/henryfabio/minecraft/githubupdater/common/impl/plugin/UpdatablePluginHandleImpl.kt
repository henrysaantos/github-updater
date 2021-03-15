package com.henryfabio.minecraft.githubupdater.common.impl.plugin

import com.henryfabio.minecraft.githubupdater.api.configuration.UpdaterConfiguration
import com.henryfabio.minecraft.githubupdater.api.exceptions.plugin.UnknownPluginAssetException
import com.henryfabio.minecraft.githubupdater.api.exceptions.plugin.UnknownPluginRepositoryException
import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePlugin
import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePluginHandle
import com.henryfabio.minecraft.githubupdater.api.update.PluginUpdate
import com.henryfabio.minecraft.githubupdater.common.impl.update.PluginUpdateImpl
import java.io.File

class UpdatablePluginHandleImpl(
    private val configuration: UpdaterConfiguration
) : UpdatablePluginHandle {

    override fun handle(updatablePlugin: UpdatablePlugin): PluginUpdate? {
        val repository = updatablePlugin.findRepository() ?: throw UnknownPluginRepositoryException(updatablePlugin)
        repository.refreshReleases()

        val release = repository.getLatestRelease {
            configuration.allowPreReleases() == it.isPreRelease
        } ?: return null

        if (release.tag != updatablePlugin.version) {
            val pluginAsset = release.pluginAsset ?: throw UnknownPluginAssetException(updatablePlugin)
            val outputFile = File(configuration.downloadsDirectory(), updatablePlugin.name + ".jar")

            return PluginUpdateImpl(
                updatablePlugin,
                pluginAsset,
                release.tag,
                outputFile
            )
        }

        return null
    }

}