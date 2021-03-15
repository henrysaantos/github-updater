package com.henryfabio.minecraft.githubupdater.common.utils

import java.net.URL
import java.net.URLConnection

object HttpUtils {

    fun connect(url: URL): URLConnection? {
        val connection = url.openConnection()

        connection.setRequestProperty("Accept", "application/octet-stream")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")

        connection.connect()
        return connection
    }

}