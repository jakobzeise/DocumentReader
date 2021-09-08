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
import kotlinx.android.synthetic.main.activity_section.*

var projectNumber = -1
var fileName = ""
var fileContent = ""
var readList = mutableListOf<Boolean>()
var falseList = mutableListOf<Boolean>()

class SectionActivity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section)

        sharedPreferences = getSharedPreferences("sectionReader", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()


        projectNumber = intent.getIntExtra("projectNumber", -1)
        fileName = listOfProjects[projectNumber].fileName
        fileContent = listOfProjects[projectNumber].fileContent

        val sectionList = fileContent.let { fileReader.getSectionsFromString(it) }
        val readListFromReadingActivity = intent.getBooleanArrayExtra("readList")
        readListFromReadingActivity?.let {
            val json = Gson().toJson(readListFromReadingActivity)
            if (editor != null) {
                editor.putString("readListKey", json)
                editor.apply()
            }
        }

        val temp = sharedPreferences?.getString("readListKey", null)
        if (!temp.isNullOrEmpty()) {
            readList = Gson().fromJson<ArrayList<Boolean>>(temp)
        }

        if (readList.isNullOrEmpty()) {

            readList = if (intent.getBooleanArrayExtra("readList") == null) {
                for ((_) in sectionList.withIndex()) {
                    falseList.add(false)
                }
                falseList
            } else {
                intent.getBooleanArrayExtra("readList")!!.toMutableList()
            }
        } else {
            falseList = readList
        }


        val numberList = mutableListOf<Int>()

        var i = 1
        for (section in sectionList) {
            numberList.add(i++)
        }

        textViewProjectName.text = fileName

        GridLayoutManager(
            this,
            3,
            RecyclerView.VERTICAL,
            false
        ).apply {
            recyclerViewSectionActivity.layoutManager = this
        }
        recyclerViewSectionActivity.adapter = RecyclerAdapterSectionActivity(numberList)
    }

    inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
}