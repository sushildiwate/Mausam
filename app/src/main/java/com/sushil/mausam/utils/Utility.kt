package com.sushil.mausam.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Long.getDayName(): String {
    val formatter: DateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    return formatter.format(Date(this.addMillisToTimestamp()))
}

fun Long.addMillisToTimestamp(): Long {
    if (this.toString().length <= 10) {
        return TimeUnit.SECONDS.toMillis(this)
    }
    return this
}

fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")