package com.henryfabio.githubupdater.commons.plugin;

import com.henryfabio.githubupdater.commons.github.plugin.repository.PluginRepository;
import com.henryfabio.githubupdater.commons.plugin.console.PluginConsole;
import com.henryfabio.githubupdater.commons.plugin.description.PluginDescription;

import java.io.File;
import java.util.logging.Logger;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public interface UpdaterPlugin extends PluginConsole {

    String getName();

    PluginDescription getDescription();

    PluginRepository getRepository();

    Logger getLogger();

    File getPluginFile();

    File getUpdatesFolder();

    @Override
    default void info(String message) {
        Logger logger = getLogger();
        logger.info("[INFO] " + message);
    }

    @Override
    default void error(String message) {
        Logger logger = getLogger();
        logger.warning("[ERROR] " + message);
    }

}
