package com.smallraw.time.utils

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

@Throws(IOException::class)
fun is2String(inputStream: InputStream): String {
    val outputStream = ByteArrayOutputStream()
    var i: Int = -1

    while (i != -1) {
        outputStream.write(i)
        i = inputStream.read()
    }
    return String(outputStream.toByteArray(), Charsets.UTF_8)
}