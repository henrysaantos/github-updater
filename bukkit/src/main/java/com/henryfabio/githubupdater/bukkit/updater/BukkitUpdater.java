package com.henryfabio.githubupdater.bukkit.updater;

import com.henryfabio.githubupdater.bukkit.plugin.BukkitUpdaterPlugin;
import com.henryfabio.githubupdater.commons.Updater;
import com.henryfabio.githubupdater.commons.configuration.UpdaterConfiguration;
import com.henryfabio.githubupdater.commons.github.plugin.repository.PluginRepository;
import com.henryfabio.githubupdater.commons.plugin.UpdaterPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class BukkitUpdater extends Updater {

    public BukkitUpdater(ConfigurationSection section) {
        super(
                "bukkit",
                UpdaterConfiguration.builder()
                        .githubToken(section.getString("token"))
                        .githubUser(section.getString("user"))
                        .build());
    }

    @Override
    public UpdaterPlugin createUpdaterPlugin(String name, PluginRepository repository) {
        return new BukkitUpdaterPlugin(Bukkit.getPluginManager().getPlugin(name), repository);
    }

}
