package com.henryfabio.minecraft.githubupdater.common.impl.update

import com.henryfabio.minecraft.githubupdater.api.github.asset.Asset
import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePlugin
import com.henryfabio.minecraft.githubupdater.api.update.PluginUpdate
import com.henryfabio.minecraft.githubupdater.common.utils.IOUtils
import java.io.File

class PluginUpdateImpl(
    private val plugin: UpdatablePlugin,
    private val pluginAsset: Asset,
    private val version: String,
    private val downloadFile: File
) : PluginUpdate {

    override fun replacePluginFile() {
        try {
            IOUtils.writeFile(downloadFile, plugin.pluginFile)
        } catch (ignore: Throwable) {
        }
    }

    override fun getPlugin() = plugin

    override fun getPluginAsset() = pluginAsset

    override fun getVersion() = version

    override fun getDownloadFile() = downloadFile

    override fun hashCode(): Int {
        return plugin.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other?.hashCode()
    }

}