package com.jakobzeise.documentreader.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakobzeise.documentreader.R
import com.jakobzeise.documentreader.modell.Projects
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


private const val LOGGING_TAG = "LoggingTag"

var listOfProjects = mutableListOf<Projects>()
var uri: Uri? = null
var fileName: String = ""
var fileReader = FileReader()
var fileContent = ""


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonAddProject.setOnClickListener {

            //intent for opening the fileChooser
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
        }
    }

    //This is happening when you click on a file
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Checks if it is the right input
        if (requestCode == 111 && resultCode == RESULT_OK) {

            //The Uri of the file
            uri = data?.data

            //The fileName
            fileName = uri?.let { fileReader.getFileName(it, contentResolver).toString() }.toString()

            //The fileContent
            fileContent = uri?.let { fileReader.readTextFromUri(it, contentResolver) }.toString()

            var sectionList = mutableListOf<>()
            Log.d(LOGGING_TAG, "fileContent : $fileContent")
            Log.d(LOGGING_TAG, "fileName : $fileName")
            Log.d(LOGGING_TAG, "uri : $uri")


            listOfProjects.add(Projects(fileName, uri, fileContent, mutableListOf()))

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = RecyclerAdapter(listOfProjects)
        }
    }

    fun getUri(): Uri? {
        return uri
    }

    fun getFileName(): String? {
        return fileName
    }

}