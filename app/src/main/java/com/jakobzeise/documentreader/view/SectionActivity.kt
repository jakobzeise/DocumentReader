package com.jakobzeise.documentreader.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakobzeise.documentreader.R
import com.jakobzeise.documentreader.modell.ReadList
import kotlinx.android.synthetic.main.activity_section.*

var projectNumber = -1
var fileName = ""
var fileContent = ""
var readListProjects = mutableListOf<ReadList>()
var readList = mutableListOf<Boolean>()
var falseList = mutableListOf<Boolean>()

class SectionActivity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section)

        //shared preferences for saving the data
        sharedPreferences = getSharedPreferences("sectionReader", Context.MODE_PRIVATE)

        //initialises the editor
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()

        //current project number from the main Activity intent or the Readingactivity Intent
        projectNumber = intent.getIntExtra("projectNumber", -1)

        //fileName and fileContent from the public projectList
        fileName = listOfProjects[projectNumber].fileName
        fileContent = listOfProjects[projectNumber].fileContent

        //separates the fileContent of the current Project into multiple sections
        val sectionList = fileContent.let {
            fileReader.getSectionsFromString(it)
        }

        //gets the ReadList from the readingActivity
        val readListFromReadingActivity = intent.getBooleanArrayExtra("readList")

        //Deletes the current readList from the readListWithNumber list
        readListProjects.filter {
            //deletes the current entry
            it.fileNumber != projectNumber
        }

        //Adds the readList from the readingActivtiy

        if (readListFromReadingActivity == null) {
            readListProjects.add(
                ReadList(
                    projectNumber,
                    fileReader.getSectionsFromString(fileContent) as MutableList<Boolean>
                )
            )
        } else {
            readListProjects.add(
                ReadList(
                    projectNumber,
                    readListFromReadingActivity as MutableList<Boolean>
                )
            )

        }

        //Converts the readListProjects list into a String and puts it into sharedPreferences
        val json = Gson().toJson(readListProjects)
        if (editor != null) {
            editor.putString("readListKey", json)
            editor.apply()
        }


        //if there already is a entry in sharedPreferences add this to readListProjects
        val temp = sharedPreferences?.getString("readListKey", null)
        if (!temp.isNullOrEmpty()) {
            readListProjects = Gson().fromJson<MutableList<ReadList>>(temp)
        }


        if (readListProjects[projectNumber].fileNumber == projectNumber) {

            readList = if (intent.getBooleanArrayExtra("readList") == null) {
                for ((_) in sectionList.withIndex()) {
                    falseList.add(false)
                }
                falseList
            } else {
                readListProjects[projectNumber].readList
            }
        } else {
            falseList = readList
        }

        //Sets the text of the textView to the fileName
        textViewProjectName.text = fileName

        //Adjust the RecyclerView
        GridLayoutManager(
            this,
            3,
            RecyclerView.VERTICAL,
            false
        ).apply {
            recyclerViewSectionActivity.layoutManager = this
        }

        val numberList = mutableListOf<Int>()

        var i = 1
        for (section in sectionList) {
            numberList.add(i++)
        }
        recyclerViewSectionActivity.adapter = RecyclerAdapterSectionActivity(numberList)
    }

    //Inline Function for converting a collection into a json String
    inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
}