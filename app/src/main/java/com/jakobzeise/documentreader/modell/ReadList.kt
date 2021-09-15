package com.jakobzeise.documentreader.modell

data class ReadList(
    var fileNumber: Int,
    var readList: MutableList<Boolean>
)
