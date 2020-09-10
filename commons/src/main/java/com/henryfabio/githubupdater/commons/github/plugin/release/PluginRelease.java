package com.henryfabio.githubupdater.commons.github.plugin.release;

import com.henryfabio.githubupdater.commons.github.exception.GithubException;
import com.henryfabio.githubupdater.commons.github.exception.GithubExceptionType;
import com.henryfabio.githubupdater.commons.http.download.FileDownload;
import com.henryfabio.githubupdater.commons.http.request.HttpRequest;
import lombok.Data;
import org.kohsuke.github.GHAsset;
import org.kohsuke.github.GHRelease;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Data
public final class PluginRelease implements Comparable<PluginRelease> {

    private final String version;
    private final Date createDate;
    private final URL assetUrl;

    public PluginRelease(GHRelease release) throws GithubException {
        this.version = release.getTagName();
        try {
            this.createDate = release.getCreatedAt();

            GHAsset asset = findAsset(release);
            this.assetUrl = asset != null ? asset.getUrl() : null;
        } catch (Exception e) {
            e.printStackTrace();
            throw GithubExceptionType.ASSET_LIST_NOT_FOUND.create();
        }
    }

    @Override
    public int compareTo(PluginRelease release) {
        return release.getCreateDate().compareTo(this.createDate);
    }

    public boolean downloadAsset(File outputFile, String authToken) {
        if (assetUrl == null) return true;

        try {
            HttpRequest request = new HttpRequest(
                    new URL(assetUrl.toString() + "?access_token=" + authToken)
            );
            FileDownload fileDownload = new FileDownload(request, outputFile);
            return fileDownload.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private GHAsset findAsset(GHRelease release) throws IOException {
        return release.getAssets().stream()
                .filter(asset -> asset.getName().contains(".jar"))
                .findFirst()
                .orElse(null);
    }

}
