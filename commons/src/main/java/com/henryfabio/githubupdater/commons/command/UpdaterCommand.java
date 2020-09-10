package com.henryfabio.githubupdater.commons.command;

import com.henryfabio.githubupdater.commons.Updater;
import com.henryfabio.githubupdater.commons.command.child.PluginChildCommand;
import com.henryfabio.githubupdater.commons.command.entry.CommandEntry;
import com.henryfabio.githubupdater.commons.configuration.UpdaterConfiguration;
import com.henryfabio.githubupdater.commons.manager.UpdaterManager;
import me.saiintbrisson.minecraft.command.CommandFrame;
import me.saiintbrisson.minecraft.command.command.CommandInfo;

import java.util.Set;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class UpdaterCommand<S> {

    private final String commandName;
    private final CommandFrame<?, S, ?> commandFrame;

    public UpdaterCommand(CommandFrame<?, S, ?> commandFrame) {
        this.commandName = Updater.getUpdater().getName() + "-updater";
        this.commandFrame = commandFrame;
    }

    public void registerCommands() {
        registerCommandEntry(buildMainCommand());
        registerCommandEntry(buildInfoCommand());

        PluginChildCommand<S> pluginCommand = new PluginChildCommand<>(this.commandName);
        registerCommandEntry(pluginCommand.buildPluginCommand());
        registerCommandEntry(pluginCommand.buildPluginInfoCommand());
        registerCommandEntry(pluginCommand.buildPluginUpdateCommand());
        registerCommandEntry(pluginCommand.buildPluginDeployCommand());
    }

    private void registerCommandEntry(CommandEntry<S> entry) {
        commandFrame.registerCommand(entry.getCommand(), entry.getExecutor());
    }

    private CommandEntry<S> buildMainCommand() {
        return new CommandEntry<>(
                CommandInfo.builder()
                        .name(this.commandName)
                        .permission("github-updater.*")
                        .build(),
                context -> {
                    context.sendMessage(new String[]{
                            "",
                            " §e/" + this.commandName + " plugin §7- §fLista de comandos de plugins.",
                            " §e/" + this.commandName + " repository §7- §fLista de comandos de repositório.",
                            " §e/" + this.commandName + " info §7- §fMostra algumas informações.",
                            ""
                    });
                    return true;
                }
        );
    }

    private CommandEntry<S> buildInfoCommand() {
        return new CommandEntry<>(
                CommandInfo.builder()
                        .name(this.commandName + ".info")
                        .build(),
                context -> {
                    Updater updater = Updater.getUpdater();
                    Set<String> pluginSet = updater.getPluginMap().keySet();
                    UpdaterManager manager = updater.getManager();
                    UpdaterConfiguration configuration = updater.getConfiguration();
                    context.sendMessage(new String[]{
                            "",
                            "§eInformações do atualizador:",
                            " §fPlataforma inicializada: §7" + updater.getName(),
                            " §fAtualizações baixadas: §7" + manager.getUpdatedPluginSet().size(),
                            " §fPlugins registrados §e(" + pluginSet.size() + ")§f: " +
                                    (pluginSet.isEmpty() ?
                                     "§cNenhum plugin registrado." :
                                     "§7" + String.join("§f, §7", pluginSet)),
                            "",
                            " §7* §eGithub:",
                            "    §fChave de acesso: §7" + configuration.getGithubToken().substring(0, 8) + "...",
                            "    §fUsuário utilizador: §7" + configuration.getGithubUser(),
                            ""
                    });
                    return true;
                }
        );
    }

}
