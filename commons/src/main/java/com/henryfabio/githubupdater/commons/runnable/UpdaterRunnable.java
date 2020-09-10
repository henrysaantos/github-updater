package com.henryfabio.githubupdater.commons.runnable;

import com.henryfabio.githubupdater.commons.Updater;
import com.henryfabio.githubupdater.commons.manager.UpdaterManager;
import com.henryfabio.githubupdater.commons.plugin.UpdaterPlugin;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@RequiredArgsConstructor
public final class UpdaterRunnable implements Runnable {

    private final Updater updater;

    @Override
    public void run() {
        UpdaterManager updaterManager = updater.getManager();
        Collection<UpdaterPlugin> pluginMap = updater.getPluginMap().values();
        pluginMap.forEach(updaterManager::updatePlugin);
    }

}
