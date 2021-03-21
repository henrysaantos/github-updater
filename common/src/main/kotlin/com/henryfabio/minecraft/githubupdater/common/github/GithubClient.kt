package com.henryfabio.minecraft.githubupdater.common.github

import com.henryfabio.minecraft.githubupdater.api.credentials.GithubCredentials
import com.henryfabio.minecraft.githubupdater.api.exceptions.github.GithubAuthenticationException
import org.kohsuke.github.GitHub

object GithubClient {

    lateinit var github: GitHub

    fun connect(credentials: GithubCredentials?) {
        try {
            github = if (credentials == null) {
                GitHub.connectAnonymously()
            } else {
                GitHub.connect(
                    credentials.username(),
                    credentials.accessToken()
                )
            }
        } catch (throwable: Throwable) {
            throw GithubAuthenticationException(throwable)
        }
    }

}