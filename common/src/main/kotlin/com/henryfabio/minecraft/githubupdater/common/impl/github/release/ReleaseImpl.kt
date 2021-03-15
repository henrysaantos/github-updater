package com.henryfabio.minecraft.githubupdater.common.impl.github.release

import com.henryfabio.minecraft.githubupdater.api.github.asset.Asset
import com.henryfabio.minecraft.githubupdater.api.github.release.Release
import com.henryfabio.minecraft.githubupdater.common.impl.github.asset.AssetImpl
import org.kohsuke.github.GHRelease

class ReleaseImpl(
    private val githubRelease: GHRelease
) : Release {

    override fun compareTo(other: Release?) =
        compareValues(this.creationDate, other?.creationDate)

    override fun getName() = githubRelease.name!!

    override fun getTag() = githubRelease.tagName!!

    override fun getCreationDate() = githubRelease.createdAt!!

    override fun isPreRelease() = githubRelease.isPrerelease

    override fun getGithubRelease() = githubRelease

    override fun getPluginAsset(): Asset? {
        val githubAsset = githubRelease.listAssets()
            .firstOrNull { it.name.contains(".jar") }

        return githubAsset?.let { AssetImpl(it.url) }
    }

}