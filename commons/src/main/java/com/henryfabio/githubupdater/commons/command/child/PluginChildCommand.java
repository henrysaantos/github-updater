package com.henryfabio.githubupdater.commons.command.child;

import com.henryfabio.githubupdater.commons.Updater;
import com.henryfabio.githubupdater.commons.command.entry.CommandEntry;
import com.henryfabio.githubupdater.commons.github.plugin.release.PluginRelease;
import com.henryfabio.githubupdater.commons.github.plugin.repository.PluginRepository;
import com.henryfabio.githubupdater.commons.manager.UpdaterManager;
import com.henryfabio.githubupdater.commons.plugin.UpdaterPlugin;
import com.henryfabio.githubupdater.commons.update.Update;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.command.CommandInfo;
import me.saiintbrisson.minecraft.command.command.Context;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;

import java.text.DateFormat;
import java.util.Optional;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@RequiredArgsConstructor
public final class PluginChildCommand<S> {

    private final String commandName;

    public CommandEntry<S> buildPluginCommand() {
        String commandName = this.commandName + " plugin";
        return new CommandEntry<>(
                CommandInfo.builder()
                        .name(commandName.replace(" ", "."))
                        .build(),
                context -> {
                    context.sendMessage(new String[]{
                            "",
                            " §e/" + commandName + " info <plugin>",
                            " §e/" + commandName + " update <plugin>",
                            " §e/" + commandName + " deploy <plugin>",
                            ""
                    });
                    return true;
                }
        );
    }

    public CommandEntry<S> buildPluginInfoCommand() {
        String commandName = this.commandName + " plugin info";
        return new CommandEntry<>(
                CommandInfo.builder()
                        .name(commandName.replace(" ", "."))
                        .async(true)
                        .build(),
                context -> {
                    if (context.argsCount() == 0) {
                        context.sendMessage("§cUtilize /" + commandName + " <plugin>");
                        return true;
                    }

                    UpdaterPlugin plugin = getPlugin(context.getArg(0), context);
                    if (plugin == null) return true;

                    context.sendMessage("§8Requisitando informações do plugin...");

                    UpdaterManager manager = Updater.getUpdater().getManager();
                    Update update = manager.getUpdatedPluginSet().get(plugin.getName());

                    String version = plugin.getDescription().getVersion();
                    PluginRepository repository = plugin.getRepository();
                    Optional<String> releaseVersionOpt = repository.findLastRelease()
                            .map(PluginRelease::getVersion);

                    context.sendMessage(new String[]{
                            "",
                            "§eInformações do plugin " + plugin.getName() + " (§7" + version + "§e):",
                            " §fRepositório registrado: §7" + repository.getName(),
                            " §fUltima versão: §7" + releaseVersionOpt
                                    .orElse("§cNão encontrada."),
                            " §fAtualizado: §7" + releaseVersionOpt
                                    .map(releaseVersion -> releaseVersion.equals(version) ? "§aSim" : "§cNão")
                                    .orElse("§cNão disponível."),
                            " §fVersão baixada: " +
                                    (update != null ?
                                     "§7" + update.getVersion() :
                                     "§cNenhum download executado."),
                            ""
                    });

                    return true;
                }
        );
    }

    public CommandEntry<S> buildPluginUpdateCommand() {
        String commandName = this.commandName + " plugin update";
        return new CommandEntry<>(
                CommandInfo.builder()
                        .name(commandName.replace(" ", "."))
                        .async(true)
                        .build(),
                context -> {
                    if (context.argsCount() == 0) {
                        context.sendMessage("§cUtilize /" + commandName + " <plugin> [--f]");
                        return true;
                    }

                    UpdaterPlugin plugin = getPlugin(context.getArg(0), context);
                    if (plugin == null) return true;

                    UpdaterManager manager = Updater.getUpdater().getManager();
                    boolean isDownloaded = manager.getUpdatedPluginSet().containsKey(plugin.getName());
                    if (isDownloaded && !"--f".equalsIgnoreCase(context.getArg(1))) {
                        context.sendMessage(
                                "§cEste plugin já teve sua atualização baixada! Utilize \"--f\" para forçar o download."
                        );
                        return true;
                    }

                    PluginRepository repository = plugin.getRepository();
                    context.sendMessage("§8Procurando versão mais recente do plugin...");

                    PluginRelease release = repository.findLastRelease().orElse(null);
                    if (release == null) {
                        context.sendMessage("§cNão foi possível encontrar a versão mais recente do plugin!");
                        return true;
                    }

                    context.sendMessage(
                            "§8Encontrada a versão " + release.getVersion() + " de " +
                                    DateFormat.getInstance().format(release.getCreateDate()) + "."
                    );

                    context.sendMessage("§eBaixando a versão mais recente do plugin...");
                    manager.downloadPluginUpdate(plugin, release);
                    context.sendMessage("§aA versão §7" + release.getVersion() +
                            " §ado plugin §7" + plugin.getName() +
                            " §afoi baixada com sucesso!"
                    );

                    return true;
                }
        );
    }

    public CommandEntry<S> buildPluginDeployCommand() {
        String commandName = this.commandName + " plugin deploy";
        return new CommandEntry<>(
                CommandInfo.builder()
                        .name(commandName.replace(" ", "."))
                        .async(true)
                        .build(),
                context -> {
                    if (context.argsCount() == 0) {
                        context.sendMessage("§cUtilize /" + commandName + " <plugin> [--f]");
                        return true;
                    }

                    UpdaterPlugin plugin = getPlugin(context.getArg(0), context);
                    if (plugin == null) return true;

                    String version = plugin.getDescription().getVersion();
                    context.sendMessage("§8Publicando versão " + version + " do plugin " + plugin.getName() + "...");

                    PluginRepository repository = plugin.getRepository();
                    GHRepository ghRepository = repository.getRepository();
                    if (ghRepository == null) {
                        context.sendMessage("§cNão foi possível encontrar o repositório deste plugin!");
                        return true;
                    }

                    try {
                        GHRelease releaseByTagName = ghRepository.getReleaseByTagName(version);
                        if (releaseByTagName != null) {
                            if (!"--f".equalsIgnoreCase(context.getArg(1))) {
                                context.sendMessage(
                                        "§cOps, me parece que já existe uma release com o nome " + version + "! " +
                                                "§cUtilize \"--f\" para substituir a release."
                                );
                                return true;
                            }

                            context.sendMessage("§8Deletando release da versão " + version + "...");
                            releaseByTagName.delete();
                        }

                        context.sendMessage("§8Criando release da versão " + version + "...");
                        GHRelease ghRelease = ghRepository.createRelease(version)
                                .name(version)
                                .create();

                        context.sendMessage("§8Adicionando arquivo do plugin na release...");
                        ghRelease.uploadAsset(plugin.getPluginFile(), "jar");

                        context.sendMessage("§aVersão §7" + version +
                                " §ado plugin §7" + plugin.getName() +
                                " §apublicada com sucesso!"
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }
        );
    }

    private UpdaterPlugin getPlugin(String name, Context<S> context) {
        Updater updater = Updater.getUpdater();
        UpdaterPlugin updaterPlugin = updater.getUpdaterPlugin(name);
        if (updaterPlugin == null) {
            context.sendMessage("§cEste plugin não esta registrado!");
            return null;
        }

        return updaterPlugin;
    }

}
