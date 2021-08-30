package com.jakobzeise.documentreader.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakobzeise.documentreader.R
import kotlinx.android.synthetic.main.activity_section.*

var projectNumber = -1
var fileName = ""
var fileContent = ""
var readList = mutableListOf<Boolean>()
val falseList = mutableListOf<Boolean>()

class SectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section)

        var str1 = intent.getStringExtra("projectNumberKey")
        var str2 = intent.getStringExtra("fileNameKey")
        var str3 = intent.getStringExtra("fileContentKey")


        projectNumber = intent.getIntExtra(str1, -1)

        fileName = intent.getStringExtra(str2).toString()

        fileContent = intent.getStringExtra(str3).toString()
        val sectionList = fileContent.let { fileReader.getSectionsFromString(it) }

        readList = if (intent.getBooleanArrayExtra("readList") == null) {
            for ((_) in sectionList.withIndex()) {
                falseList.add(false)
            }
            falseList
        } else {
            intent.getBooleanArrayExtra("readList")!!.toMutableList()
        }
        Log.d(TAG, "falseList :$falseList")
        Log.d(TAG, "readList : $readList")

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
}