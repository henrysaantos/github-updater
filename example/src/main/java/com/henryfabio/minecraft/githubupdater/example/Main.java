package com.henryfabio.minecraft.githubupdater.example;

import com.henryfabio.minecraft.githubupdater.api.configuration.UpdaterConfiguration;
import com.henryfabio.minecraft.githubupdater.api.credentials.GithubCredentials;
import com.henryfabio.minecraft.githubupdater.bukkit.BukkitGithubUpdater;
import com.henryfabio.minecraft.githubupdater.bukkit.plugin.BukkitUpdatablePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private BukkitGithubUpdater githubUpdater;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // This is not necessary for all plugins, just for one and get the instance to use on others
        FileConfiguration configuration = getConfig();
        this.githubUpdater = new BukkitGithubUpdater(
                this,
                UpdaterConfiguration.DEFAULT,
                GithubCredentials.builder()
                        .username(configuration.getString("username"))
                        .accessToken(configuration.getString("accessToken"))
                        .build()
        );

        // Registering plugin with your repository to update
        this.githubUpdater.registerUpdatablePlugin(new BukkitUpdatablePlugin(
                this,
                "HenryFabio/test-github-updater"
        ));
    }

    @Override
    public void onDisable() {
        // Finishing downloaded updates and moving files
        this.githubUpdater.stop();
    }

}
