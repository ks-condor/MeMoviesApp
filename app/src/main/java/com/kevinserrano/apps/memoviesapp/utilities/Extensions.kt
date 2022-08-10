package com.kevinserrano.apps.memoviesapp.utilities

import java.text.SimpleDateFormat
import java.util.*

fun String?.getYearFromFormat(dateFormat: String = "yyyy-MM-dd"): Int {
    if (this.isNullOrEmpty())
        return 1993
    val date = SimpleDateFormat(
        dateFormat,
        Locale.getDefault()
    ).parse(this) ?: Date()
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar.get(Calendar.YEAR)
}