package com.henryfabio.githubupdater.commons.http.download;

import com.henryfabio.githubupdater.commons.http.request.HttpRequest;
import lombok.Cleanup;
import lombok.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Data
public final class FileDownload {

    private final HttpRequest httpRequest;
    private final File outputFile;

    public boolean execute() {
        prepareOutputFile();

        httpRequest.connect().ifPresent(connection -> {
            try {
                @Cleanup FileOutputStream fileOutput = new FileOutputStream(outputFile);
                @Cleanup FileChannel fileChannel = fileOutput.getChannel();
                @Cleanup ReadableByteChannel readableChannel = Channels.newChannel(connection.getInputStream());

                fileChannel.transferFrom(readableChannel, 0, Long.MAX_VALUE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return outputFile.exists() && outputFile.isFile();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void prepareOutputFile() {
        if (outputFile.exists()) {
            outputFile.delete();
        } else {
            File parent = outputFile.getParentFile();
            parent.mkdirs();
        }
    }

}
