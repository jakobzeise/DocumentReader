package com.jakobzeise.documentreader.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakobzeise.documentreader.R
import com.jakobzeise.documentreader.modell.Projects
import kotlinx.android.synthetic.main.activity_main.*

var listOfProjects = mutableSetOf<Projects>()
var uri: Uri? = null
var fileReader = FileReader()
var fileNames = mutableListOf<String>()
var fileContents = mutableListOf<String>()
const val TAG = "JakobLogging"

class MainActivity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("documentReader", Context.MODE_PRIVATE)

        fileContents =
            sharedPreferences?.getStringSet("fileContents", mutableSetOf())?.toMutableList()
                ?: mutableListOf()

        fileNames =
            sharedPreferences?.getStringSet("fileNames", mutableSetOf())?.toMutableList()
                ?: mutableListOf()

        Log.d(TAG, "onCreate: fileNames: $fileNames")
        var i = fileContents.size - 1
        while (i > -1) {
            listOfProjects.add(Projects(fileNames.elementAt(i), fileContents.elementAt(i)))
            i--
        }

        Log.d(TAG, "onCreate: fileNames: $fileNames")
        Log.d(TAG, "onCreate: fileContents: $fileContents")

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapterMainActivity(listOfProjects)

        buttonAddProject.setOnClickListener {

            //intent for opening the fileChooser
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)

        }
    }

    //Get a New File/Project
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()

        //Checks if it is the right input
        if (requestCode == 111 && resultCode == RESULT_OK) {

            //The Uri of the file
            uri = data?.data


            val fileName =
                uri?.let { fileReader.getFileName(it, contentResolver).toString() }.toString()

            if (!fileNames.contains(fileName)) {
                fileNames.add(fileName)

                val fileContent =
                    uri?.let { fileReader.readTextFromUri(it, contentResolver) }.toString()
                fileContents.add(fileContent)
                Log.d(TAG, "onActivityResult: just added the fileContent: $fileContent")

                var i = 0
                while (i < fileContents.size) {
                    listOfProjects.add(Projects(fileNames.elementAt(i), fileContents.elementAt(i)))
                    i++
                }
            } else {
                Toast.makeText(this, "This file is already added", Toast.LENGTH_SHORT).show()
            }

            editor?.putStringSet("fileNames", fileNames.toMutableSet())
            editor?.putStringSet("fileContents", fileContents.toMutableSet())
            editor?.apply()

            Log.d(TAG, "onCreate: fileNames: $fileNames")
            Log.d(TAG, "onCreate: fileContents: $fileContents")
            //Update the recyclerView
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = RecyclerAdapterMainActivity(listOfProjects)
        }
    }

}