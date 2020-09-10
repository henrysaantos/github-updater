package com.henryfabio.githubupdater.commons;

import com.henryfabio.githubupdater.commons.configuration.UpdaterConfiguration;
import com.henryfabio.githubupdater.commons.github.GithubManager;
import com.henryfabio.githubupdater.commons.github.plugin.repository.PluginRepository;
import com.henryfabio.githubupdater.commons.manager.UpdaterManager;
import com.henryfabio.githubupdater.commons.plugin.UpdaterPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
@RequiredArgsConstructor
public abstract class Updater {

    @Getter private static Updater updater;

    public static Updater enable(Updater instance) {
        Updater.updater = instance;
        return instance;
    }

    private final String name;
    private final UpdaterManager manager = new UpdaterManager(this);
    private final GithubManager githubManager = new GithubManager();
    private final UpdaterConfiguration configuration;

    private final Map<String, UpdaterPlugin> pluginMap = new LinkedHashMap<>();

    public void registerPlugin(String pluginName, String repositoryName) {
        PluginRepository repository = new PluginRepository(repositoryName);
        UpdaterPlugin updaterPlugin = createUpdaterPlugin(pluginName, repository);
        this.pluginMap.put(pluginName, updaterPlugin);

        updaterPlugin.info("Plugin registered with \"" + repositoryName + "\" repository.");
    }

    public UpdaterPlugin getUpdaterPlugin(String name) {
        return pluginMap.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(name))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    abstract public UpdaterPlugin createUpdaterPlugin(String name, PluginRepository repository);

}
