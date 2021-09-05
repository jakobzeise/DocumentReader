package com.jakobzeise.documentreader.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakobzeise.documentreader.R
import com.jakobzeise.documentreader.modell.Projects
import kotlinx.android.synthetic.main.activity_main.*

var listOfProjects = mutableListOf<Projects>()
var uri: Uri? = null
var fileReader = FileReader()
var fileNames = mutableListOf<String>()
var fileContents = mutableListOf<String>()
const val TAG = "JakobLogging"
const val BOOKS = "testKey"

class MainActivity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("documentReader", Context.MODE_PRIVATE)

        val listOfProjectsString = sharedPreferences?.getString("jsonKey", null)
        if (!listOfProjectsString.isNullOrEmpty()) {
            listOfProjects = Gson().fromJson<MutableList<Projects>>(listOfProjectsString)

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = RecyclerAdapterMainActivity(listOfProjects)
        }

        if (listOfProjects.isNotEmpty()) {
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = RecyclerAdapterMainActivity(listOfProjects)
        }

        buttonAddProject.setOnClickListener {
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = RecyclerAdapterMainActivity(listOfProjects)
            //intent for opening the fileChooser
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) { //Saves the game state
        super.onSaveInstanceState(savedInstanceState)
        saveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        restoreInstanceState(savedInstanceState)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        listOfProjects = (savedInstanceState.getSerializable(BOOKS) as ArrayList<Projects>)
    }

    private fun saveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putSerializable(BOOKS, listOfProjects as ArrayList<Projects>)
    }

    //Get a New File/Project
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

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
                listOfProjects.add(
                    Projects(
                        fileNames.elementAt(listOfProjects.size),
                        fileContents.elementAt(listOfProjects.size)
                    )
                )
            } else {
                Toast.makeText(this, "This file is already added", Toast.LENGTH_SHORT).show()
            }

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = RecyclerAdapterMainActivity(listOfProjects)
        }
    }

    override fun onStop() {
        super.onStop()
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        val json = Gson().toJson(listOfProjects)
        if (editor != null) {
            editor.putString("jsonKey", json)
            editor.apply()
        }
    }

    inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
}