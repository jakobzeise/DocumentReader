package com.jakobzeise.documentreader.view

import android.app.Application
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

private const val LOGGING_TAG = "LoggingTag"

class FileReader : Application() {

    fun getFileName(uri: Uri, contentResolver: ContentResolver): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (it != null && it.moveToFirst()) {
                    result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
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


    fun getSectionsFromString(string: String): MutableList<String> {

        //Divide words into wordList
        val wordList = string.split(" ")

        //Get the number of sections needed
        val numberOfSections = wordList.size / 1000 + 1

        //create a list of sections
        val sectionList = mutableListOf<String>()

        //create Sections in sectionlist
        var i = numberOfSections
        while (i-- > 0) {
            sectionList.add("")
        }

        //Add words into index
        for ((index, words) in wordList.withIndex()) {
            sectionList[index / 1000] = sectionList[index / 1000] + " $words "
        }

        return sectionList

    }
}