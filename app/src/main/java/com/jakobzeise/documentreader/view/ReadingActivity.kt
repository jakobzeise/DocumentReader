package com.jakobzeise.documentreader.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.jakobzeise.documentreader.R
import kotlinx.android.synthetic.main.activity_reading.*
import kotlinx.android.synthetic.main.item_recycler.*
import java.io.File

class ReadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        var fileName = intent.getStringExtra("fileName")
        var fileContent = intent.getStringExtra("fileContent")
        textViewContent.text = fileContent
        textViewTitle.text = fileName

        buttonClose.setOnClickListener {
            var intentGoHome = Intent(it.context, MainActivity::class.java)
            startActivity(intentGoHome)
        }

    }
}