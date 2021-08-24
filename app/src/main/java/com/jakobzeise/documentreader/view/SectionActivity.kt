package com.jakobzeise.documentreader.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakobzeise.documentreader.R
import kotlinx.android.synthetic.main.activity_section.*


private const val LOGGING_TAG = "LoggingTag"

class SectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section)

        val sectionList = intent.getStringArrayListExtra("sectionList")
        val fileName = intent.getStringExtra("fileName")

        if (sectionList != null) {
            Log.d(LOGGING_TAG, "sectionList : $sectionList")
        }
        val numberList = mutableListOf<Int>()

        var i = 1
        if (sectionList != null) {
            for (section in sectionList) {
                numberList.add(i++)
            }
        }

        Log.d(LOGGING_TAG, "numberList : $numberList")
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