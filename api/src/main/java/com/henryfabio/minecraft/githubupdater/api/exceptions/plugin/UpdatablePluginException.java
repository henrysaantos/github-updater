package com.henryfabio.minecraft.githubupdater.api.exceptions.plugin;

import com.henryfabio.minecraft.githubupdater.api.plugin.UpdatablePlugin;
import lombok.Getter;

@Getter
public abstract class UpdatablePluginException extends Exception {

    public UpdatablePluginException(String message, UpdatablePlugin updatablePlugin) {
        super(String.format(message, updatablePlugin.getName()));
    }

}
