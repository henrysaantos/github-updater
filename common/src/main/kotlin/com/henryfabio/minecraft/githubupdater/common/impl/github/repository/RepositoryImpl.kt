package com.henryfabio.minecraft.githubupdater.common.impl.github.repository

import com.henryfabio.minecraft.githubupdater.api.github.release.Release
import com.henryfabio.minecraft.githubupdater.api.github.repository.Repository
import com.henryfabio.minecraft.githubupdater.common.impl.github.release.ReleaseImpl
import org.kohsuke.github.GHRepository
import java.util.function.Predicate

class RepositoryImpl(
    private val githubRepository: GHRepository
) : Repository {

    private val releases = mutableListOf<Release>()

    override fun refreshReleases() {
        releases.clear()
        releases.addAll(githubRepository.listReleases().map { ReleaseImpl(it) })
    }

    override fun getLatestRelease(filter: Predicate<Release>) = releases
        .filter { filter.test(it) }
        .maxOrNull()

    override fun getName() = githubRepository.name!!

    override fun getGithubRepository() = githubRepository

    override fun getReleases(): List<Release> = releases

}