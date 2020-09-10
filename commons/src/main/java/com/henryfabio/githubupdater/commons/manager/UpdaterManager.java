package com.henryfabio.githubupdater.commons.manager;

import com.henryfabio.githubupdater.commons.Updater;
import com.henryfabio.githubupdater.commons.github.plugin.release.PluginRelease;
import com.henryfabio.githubupdater.commons.github.plugin.repository.PluginRepository;
import com.henryfabio.githubupdater.commons.plugin.UpdaterPlugin;
import com.henryfabio.githubupdater.commons.update.Update;
import lombok.Cleanup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
@RequiredArgsConstructor
public final class UpdaterManager {

    private final Updater updater;
    private final Map<String, Update> updatedPluginSet = new LinkedHashMap<>();

    public void updatePlugin(UpdaterPlugin plugin) {
        Thread thread = new Thread(() -> {
            PluginRepository repository = plugin.getRepository();

            PluginRelease release = repository.findLastRelease().orElse(null);
            if (release == null) return;

            Update lastUpdate = updatedPluginSet.get(plugin.getName());
            if (lastUpdate != null) {
                if (lastUpdate.getVersion().equalsIgnoreCase(release.getVersion())) return;
                else {
                    File updateFile = lastUpdate.getFile();
                    if (updateFile.exists() && updateFile.delete()) {
                        updatedPluginSet.remove(plugin.getName());
                    }
                }
            }

            boolean equalsVersion = isEqualsVersion(plugin, release);
            if (!equalsVersion) {
                plugin.info("The plugin need update! " +
                        "Current version: " + plugin.getDescription().getVersion() + " - " +
                        "New version: " + release.getVersion());
                downloadPluginUpdate(plugin, release);
            }
        });
        thread.start();
    }

    @SneakyThrows
    public boolean isEqualsVersion(UpdaterPlugin plugin, PluginRelease repositoryRelease) {
        GHRepository repository = plugin.getRepository().getRepository();
        if (repository == null) return true;

        String version = plugin.getDescription().getVersion();
        GHRelease release = repository.getReleaseByTagName(version);
        if (release == null) return true;

        System.out.println(version.equalsIgnoreCase(repositoryRelease.getVersion()));
        System.out.println(release.getCreatedAt().equals(repositoryRelease.getCreateDate()));
        return version.equalsIgnoreCase(repositoryRelease.getVersion()) &&
                release.getCreatedAt().equals(repositoryRelease.getCreateDate());
    }

    public void completePluginUpdates() {
        updatedPluginSet.forEach((key, value) ->
                completePluginUpdate(updater.getUpdaterPlugin(key), value.getFile())
        );
    }

    public void completePluginUpdate(UpdaterPlugin plugin, File pluginFile) {
        if (!pluginFile.exists()) return;
        writeFile(pluginFile, plugin.getPluginFile());
    }

    public void downloadPluginUpdate(UpdaterPlugin plugin, PluginRelease release) {
        plugin.info("Downloading plugin update...");

        File pluginFile = new File(plugin.getUpdatesFolder(), plugin.getName() + ".jar");
        updatedPluginSet.put(plugin.getName(), new Update(release.getVersion(), pluginFile));

        boolean downloadSuccess = release.downloadAsset(
                pluginFile,
                updater.getConfiguration().getGithubToken()
        );

        if (!downloadSuccess) {
            updatedPluginSet.remove(plugin.getName());
            plugin.info("Couldn't download plugin update. Please restart your server!");
            return;
        }

        plugin.info("The download of the plugin update was successful! Downloaded version: " + release.getVersion());
    }

    private void writeFile(File file, File outputFile) {
        try {
            @Cleanup InputStream input = new FileInputStream(file);
            @Cleanup OutputStream output = new FileOutputStream(outputFile);
            byte[] buffer = new byte[4096];

            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
