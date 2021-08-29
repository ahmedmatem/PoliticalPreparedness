package com.example.android.politicalpreparedness.election.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.politicalpreparedness.utils.exts.dateToString
import java.util.*

@BindingAdapter("text")
fun TextView.parseDate(date: Date) {
    text = date.dateToString()
}