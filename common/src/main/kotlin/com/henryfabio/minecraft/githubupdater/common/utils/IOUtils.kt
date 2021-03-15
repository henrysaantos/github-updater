package com.henryfabio.minecraft.githubupdater.common.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object IOUtils {

    fun writeFile(file: File, outputFile: File) {
        FileInputStream(file).use { inputStream ->
            FileOutputStream(outputFile).use { outputStream ->
                val buffer = ByteArray(4096)
                var length: Int

                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(buffer, 0, length)
                }
            }
        }
    }

    fun getExecutableJarFromClass(clazz: Class<*>): File {
        val protectionDomain = clazz.protectionDomain
        val locationUri = protectionDomain.codeSource.location.toURI()
        return File(locationUri.path)
    }

}