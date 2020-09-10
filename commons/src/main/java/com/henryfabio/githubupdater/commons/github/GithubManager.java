package com.henryfabio.githubupdater.commons.github;

import com.henryfabio.githubupdater.commons.Updater;
import com.henryfabio.githubupdater.commons.configuration.UpdaterConfiguration;
import com.henryfabio.githubupdater.commons.github.consumer.GithubConsumer;
import com.henryfabio.githubupdater.commons.github.exception.GithubException;
import com.henryfabio.githubupdater.commons.github.exception.GithubExceptionType;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class GithubManager {

    private GitHub gitHub;

    public void enable(Updater updater) throws GithubException {
        UpdaterConfiguration configuration = updater.getConfiguration();
        try {
            this.gitHub = GitHub.connectUsingOAuth(
                    configuration.getGithubToken()
            );
        } catch (IOException e) {
            throw GithubExceptionType.AUTH_FAILED.create();
        }
    }

    public <T> T consume(GithubConsumer<T> consumer) {
        if (gitHub == null) return null;
        return consumer.accept(gitHub);
    }

    public GHRepository getRepository(String name) throws GithubException {
        if (gitHub == null) return null;

        UpdaterConfiguration configuration = Updater.getUpdater().getConfiguration();
        try {
            return gitHub.getRepository(configuration.getGithubUser() + "/" + name);
        } catch (IOException e) {
            throw GithubExceptionType.REPOSITORY_NOT_FOUND.create();
        }
    }

}
