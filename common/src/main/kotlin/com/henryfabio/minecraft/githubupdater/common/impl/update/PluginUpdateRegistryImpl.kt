package com.henryfabio.minecraft.githubupdater.common.impl.update

import com.henryfabio.minecraft.githubupdater.api.update.PluginUpdate
import com.henryfabio.minecraft.githubupdater.api.update.PluginUpdateRegistry

class PluginUpdateRegistryImpl : PluginUpdateRegistry {

    private val pluginUpdates = mutableSetOf<PluginUpdate>()

    override fun registerPluginUpdate(pluginUpdate: PluginUpdate) {
        pluginUpdates.add(pluginUpdate)
    }

    override fun removePluginUpdate(pluginUpdate: PluginUpdate) {
        pluginUpdates.remove(pluginUpdate)
    }

    override fun getPluginUpdateByName(name: String) =
        pluginUpdates.firstOrNull { it.plugin.name == name }

    override fun getPluginUpdates() = pluginUpdates

}