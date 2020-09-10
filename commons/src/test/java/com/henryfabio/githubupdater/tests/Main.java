package com.henryfabio.githubupdater.tests;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public class Main {

    public static void main(String[] args) {
        try {
            GitHub gitHub = GitHub.connectUsingOAuth("75953b5f57d57917d1d9599be5caf8f71f60f980");

            GHRepository repository = gitHub.getRepository("RedeLegit/LegitReport");
            System.out.println(repository.listReleases().toList().get(0).getAssets().get(0).getBrowserDownloadUrl());

            HttpURLConnection connection = gitHub.getConnector().connect(new URL(
                    repository.listReleases().toList().get(0).getAssets().get(0).getUrl().toString() +
                            "?access_token=75953b5f57d57917d1d9599be5caf8f71f60f980"
            ));

            connection.setRequestProperty("Accept", "application/octet-stream");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            connection.connect();

//            HttpGet httpGet = new HttpGet(
//                    repository.listReleases().toList().get(0).getAssets().get(0).getUrl().toString() +
//                            "?access_token=75953b5f57d57917d1d9599be5caf8f71f60f980"
//            );
//            httpGet.setHeader("Accept", "application/octet-stream");
//
//            HttpClient httpClient = HttpClientBuilder.create()
//                    .setDefaultRequestConfig(RequestConfig.custom()
//                            .setCookieSpec(CookieSpecs.STANDARD).build()
//                    )
//                    .build();
//            HttpResponse response = httpClient.execute(httpGet);

            System.out.println(connection.getResponseCode());

            FileOutputStream fileOutput = new FileOutputStream(new File("test.jar"));

            ReadableByteChannel channel = Channels.newChannel(connection.getInputStream());
            FileChannel fileChannel = fileOutput.getChannel();
            fileChannel.transferFrom(channel, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
