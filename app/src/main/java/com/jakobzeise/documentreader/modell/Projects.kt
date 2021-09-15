package com.jakobzeise.documentreader.modell

import java.io.Serializable

data class Projects(
    val fileName: String,
    val fileContent: String,
) : Serializable