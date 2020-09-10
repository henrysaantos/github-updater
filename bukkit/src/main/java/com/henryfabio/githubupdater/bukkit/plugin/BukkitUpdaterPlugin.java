package com.henryfabio.githubupdater.bukkit.plugin;

import com.henryfabio.githubupdater.commons.github.plugin.repository.PluginRepository;
import com.henryfabio.githubupdater.commons.plugin.UpdaterPlugin;
import com.henryfabio.githubupdater.commons.plugin.description.PluginDescription;
import lombok.Data;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.logging.Logger;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Data
public final class BukkitUpdaterPlugin implements UpdaterPlugin {

    private final String name;
    private final PluginDescription description;
    private final PluginRepository repository;

    private final Logger logger;

    private final File pluginFile;
    private final File updatesFolder;

    public BukkitUpdaterPlugin(Plugin plugin, PluginRepository repository) {
        this.name = plugin.getName();
        this.description = new PluginDescription(plugin.getDescription().getVersion());
        this.repository = repository;

        this.logger = plugin.getLogger();

        this.pluginFile = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getFile());
        this.updatesFolder = new File(plugin.getDataFolder(), "updates");
        updatesFolder.mkdirs();
    }

}
