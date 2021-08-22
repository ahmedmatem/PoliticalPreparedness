package com.example.android.politicalpreparedness.utils.exts

import java.text.SimpleDateFormat
import java.util.*

const val DefaultDateFormat: String = "EEE MMM dd HH:mm:ss z yyyy"

fun Date.dateToString(format: String): String {
    // simple date formatter
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.format(this)
}