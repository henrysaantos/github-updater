package com.henryfabio.githubupdater.commons.github.plugin.repository;

import com.henryfabio.githubupdater.commons.Updater;
import com.henryfabio.githubupdater.commons.github.exception.GithubException;
import com.henryfabio.githubupdater.commons.github.plugin.release.PluginRelease;
import lombok.Data;
import org.kohsuke.github.GHRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Data
public final class PluginRepository {

    private final String name;

    public Optional<PluginRelease> findLastRelease() {
        List<PluginRelease> releases = findReleases();
        releases.sort(PluginRelease::compareTo);

        try {
            return Optional.of(releases.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<PluginRelease> findReleases() {
        try {
            GHRepository repository = getRepository();
            if (repository == null) return Collections.emptyList();

            return repository.listReleases().toList().stream()
                    .map(release -> {
                        try {
                            return new PluginRelease(release);
                        } catch (GithubException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public GHRepository getRepository() {
        try {
            return Updater.getUpdater().getGithubManager().getRepository(this.name);
        } catch (GithubException e) {
            e.printStackTrace();
        }

        return null;
    }

}
