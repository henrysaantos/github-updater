package com.henryfabio.githubupdater.commons.http.request;

import lombok.Data;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Data
public class HttpRequest {

    private final URL url;

    public Optional<HttpURLConnection> connect() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Accept", "application/octet-stream");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(connection);
    }

}
