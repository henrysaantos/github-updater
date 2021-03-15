package com.henryfabio.minecraft.githubupdater.common.impl.github.asset

import com.henryfabio.minecraft.githubupdater.api.github.asset.Asset
import com.henryfabio.minecraft.githubupdater.common.utils.HttpUtils
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.util.concurrent.CompletableFuture

class AssetImpl(
    private val downloadUrl: URL
) : Asset {

    override fun download(outputFile: File, accessToken: String): CompletableFuture<Void> {
        deleteOutputFile(outputFile)

        val downloadUrlWithAuthorization = URL("$downloadUrl?access_token=$accessToken")
        return CompletableFuture.runAsync {
            HttpUtils.connect(downloadUrlWithAuthorization)?.let {
                val outputStream = FileOutputStream(outputFile)
                outputStream.channel.use { fileChannel ->
                    Channels.newChannel(it.getInputStream()).use { readableChannel ->
                        fileChannel.transferFrom(readableChannel, 0, Long.MAX_VALUE)
                    }
                }
                outputStream.close()
            }
        }
    }

    override fun getDownloadUrl() = downloadUrl

    private fun deleteOutputFile(outputFile: File) {
        if (outputFile.exists()) {
            outputFile.delete()
        } else {
            outputFile.parentFile?.mkdirs()
        }
    }

}