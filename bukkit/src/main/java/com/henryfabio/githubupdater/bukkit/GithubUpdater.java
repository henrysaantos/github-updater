package com.henryfabio.githubupdater.bukkit;

import com.henryfabio.githubupdater.bukkit.updater.BukkitUpdater;
import com.henryfabio.githubupdater.commons.Updater;
import com.henryfabio.githubupdater.commons.command.UpdaterCommand;
import com.henryfabio.githubupdater.commons.github.exception.GithubException;
import com.henryfabio.githubupdater.commons.runnable.UpdaterRunnable;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public final class GithubUpdater extends JavaPlugin {

    private final CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();

    @Override
    public void onLoad() {
        saveDefaultConfig();

        FileConfiguration configuration = getConfig();
        Updater updater = Updater.enable(new BukkitUpdater(configuration.getConfigurationSection("github")));

        Thread thread = new Thread(() -> {
            try {
                updater.getGithubManager().enable(updater);
                completableFuture.complete(true);
            } catch (GithubException e) {
                e.printStackTrace();
                completableFuture.complete(false);
            }
        });
        thread.start();
    }

    @Override
    public void onEnable() {
        this.completableFuture.whenComplete((success, throwable) -> {
            Logger logger = getLogger();
            if (!success) {
                logger.warning("Plugin enabled with error!");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }

            BukkitFrame bukkitFrame = new BukkitFrame(this);
            UpdaterCommand<CommandSender> updaterCommand = new UpdaterCommand<>(bukkitFrame);
            updaterCommand.registerCommands();

            Updater updater = Updater.getUpdater();
            Bukkit.getScheduler().runTaskTimer(
                    this,
                    new UpdaterRunnable(updater),
                    20 * 5,
                    20 * 5
            );

            logger.info("Plugin enabled with successfully!");
        });
    }

    @Override
    public void onDisable() {
        Updater updater = Updater.getUpdater();
        updater.getManager().completePluginUpdates();
    }

}
