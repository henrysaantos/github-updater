package com.henryfabio.minecraft.githubupdater.bukkit.plugin

import com.henryfabio.minecraft.githubupdater.common.impl.plugin.AbstractUpdatablePlugin
import com.henryfabio.minecraft.githubupdater.common.utils.IOUtils
import org.bukkit.plugin.Plugin

class BukkitUpdatablePlugin(
    plugin: Plugin,
    repositoryName: String
) : AbstractUpdatablePlugin(
    plugin.name,
    plugin.description.version,
    repositoryName,
    IOUtils.getExecutableJarFromClass(plugin::class.java)
)