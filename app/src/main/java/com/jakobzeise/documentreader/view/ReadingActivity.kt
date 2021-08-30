package com.jakobzeise.documentreader.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jakobzeise.documentreader.R
import kotlinx.android.synthetic.main.activity_reading.*

class ReadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)


        val projectNumber = intent.getIntExtra("projectNumber", -1)
        val sectionNumber = intent.getIntExtra("sectionNumber", -1)
        Log.d(TAG, "sectionNumber: $sectionNumber")
        val fileName = intent.getStringExtra("fileName")
        val fileContent = intent.getStringExtra("fileContent")
        val readList = intent.getBooleanArrayExtra("readList")

        val sectionContent =
            fileContent?.let { fileReader.getSectionsFromString(it)[sectionNumber] }
        textViewContent.text = sectionContent
        textViewTitle.text = fileName

        buttonClose.setOnClickListener {
            val intentGoHome = Intent(it.context, MainActivity::class.java)
            startActivity(intentGoHome)
        }

        buttonMarkAsRead.setOnClickListener {
            readList?.set(sectionNumber, true)
            val intentOpenSectionActivity = Intent(it.context, SectionActivity::class.java)
            intentOpenSectionActivity.putExtra("projectNumber", projectNumber)
            intentOpenSectionActivity.putExtra("fileName", fileName)
            intentOpenSectionActivity.putExtra("fileContent", fileContent)
            intentOpenSectionActivity.putExtra("readList", readList)
            startActivity(intentOpenSectionActivity)
        }

    }
}