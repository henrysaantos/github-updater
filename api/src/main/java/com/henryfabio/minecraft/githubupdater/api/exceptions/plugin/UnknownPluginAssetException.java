package com.henryfabio.minecraft.githubupdater.api.exceptions.plugin;

import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePlugin;

public final class UnknownPluginAssetException extends UpdatablePluginException {

    private static final String EXCEPTION_MESSAGE = "The latest plugin \"%s\" release hasn't valid assets to download!";

    public UnknownPluginAssetException(UpdatablePlugin updatablePlugin) {
        super(EXCEPTION_MESSAGE, updatablePlugin);
    }

}
