package com.henryfabio.minecraft.githubupdater.common.impl.plugin

import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePlugin
import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePluginRegistry

class UpdatablePluginRegistryImpl :
    UpdatablePluginRegistry {

    private val updatablePlugins = mutableMapOf<String, UpdatablePlugin>()

    override fun getUpdatablePluginByName(name: String) =
        updatablePlugins[name]

    override fun registerUpdatablePlugin(updatablePlugin: UpdatablePlugin) {
        updatablePlugins[updatablePlugin.name] = updatablePlugin
    }

    override fun getUpdatablePlugins() = updatablePlugins

}