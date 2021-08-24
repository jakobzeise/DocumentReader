package com.jakobzeise.documentreader.view

import android.app.Application
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FileReader : Application() {

    fun getFileName(uri: Uri, contentResolver: ContentResolver): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            cursor.use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result!!.substring(cut + 1)
            }
        }
        return result
    }

    @Throws(IOException::class)
    fun readTextFromUri(uri: Uri, contentResolver: ContentResolver): String {
        val stringBuilder = StringBuilder()
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return stringBuilder.toString()
    }

    fun getSectionsFromString(string: String): MutableList<String>{
        var numberOfSections = string.length % 1000
        var firstSection = string.slice(0..1000)
        return mutableListOf(firstSection)
    }
}