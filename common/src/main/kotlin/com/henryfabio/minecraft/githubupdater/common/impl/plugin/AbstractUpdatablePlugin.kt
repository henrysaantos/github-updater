package com.henryfabio.minecraft.githubupdater.common.impl.plugin

import com.henryfabio.minecraft.githubupdater.api.github.repository.Repository
import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePlugin
import com.henryfabio.minecraft.githubupdater.common.github.GithubClient
import com.henryfabio.minecraft.githubupdater.common.impl.github.repository.RepositoryImpl
import java.io.File

open class AbstractUpdatablePlugin(
    private val name: String,
    private val version: String,
    private val repositoryName: String,
    private val pluginFile: File
) : UpdatablePlugin {

    override fun findRepository(): Repository? {
        return GithubClient.github.getRepository(repositoryName)?.let {
            return RepositoryImpl(it)
        }
    }

    override fun getName() = name

    override fun getVersion() = version

    override fun getRepositoryName() = repositoryName

    override fun getPluginFile() = pluginFile

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other?.hashCode()
    }

}