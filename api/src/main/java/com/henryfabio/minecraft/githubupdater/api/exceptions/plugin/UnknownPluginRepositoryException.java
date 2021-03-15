package com.henryfabio.minecraft.githubupdater.api.exceptions.plugin;

import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePlugin;

public final class UnknownPluginRepositoryException extends UpdatablePluginException {

    private static final String EXCEPTION_MESSAGE = "The provided repository name for plugin \"%s\" not exists!";

    public UnknownPluginRepositoryException(UpdatablePlugin updatablePlugin) {
        super(EXCEPTION_MESSAGE, updatablePlugin);
    }

}
