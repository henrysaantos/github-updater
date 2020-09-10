package com.henryfabio.githubupdater.commons.command.entry;

import lombok.Data;
import me.saiintbrisson.minecraft.command.command.CommandInfo;
import me.saiintbrisson.minecraft.command.executor.CommandExecutor;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Data
public final class CommandEntry<S> {

    private final CommandInfo command;
    private final CommandExecutor<S> executor;

}
