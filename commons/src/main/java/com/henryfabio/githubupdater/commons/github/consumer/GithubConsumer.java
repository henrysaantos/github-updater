package com.henryfabio.githubupdater.commons.github.consumer;

import org.kohsuke.github.GitHub;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@FunctionalInterface
public interface GithubConsumer<T> {

    T accept(GitHub gitHub);

}
