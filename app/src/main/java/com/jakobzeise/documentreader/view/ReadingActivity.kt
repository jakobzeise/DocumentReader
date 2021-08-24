package com.jakobzeise.documentreader.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakobzeise.documentreader.R
import kotlinx.android.synthetic.main.activity_reading.*

class ReadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        val fileName = intent.getStringExtra("fileName")
        var fileContent = intent.getStringExtra("fileContent")
        val section = intent.getStringExtra("section")?.toInt()
        val sectionList = intent.getStringArrayListExtra("sectionList")

        val sectionContent = section?.minus(1)?.let { sectionList?.get(it) }
        textViewContent.text = sectionContent
        textViewTitle.text = fileName

        buttonClose.setOnClickListener {
            val intentGoHome = Intent(it.context, MainActivity::class.java)
            startActivity(intentGoHome)
        }

    }
}