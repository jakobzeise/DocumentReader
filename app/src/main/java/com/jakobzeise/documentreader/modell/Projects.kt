package com.jakobzeise.documentreader.modell

import android.net.Uri

data class Projects(
    val fileName: String,
    val uri: Uri?,
    val fileContent: String,
    val sections: MutableList<String>
)