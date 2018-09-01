package com.aksh.dev.home.halve.utils

import java.util.*

class Date constructor(private val calendar: Calendar) {
    private val TWO_DEC = "%02d"
    private val day: Int
    private val month: Int
    private val year: Int

    private val hours: Int
    private val minutes: Int
    private val am_pm: String

    init {
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH) + 1
        year = calendar.get(Calendar.YEAR)

        hours = calendar.get(Calendar.HOUR_OF_DAY) % 12
        minutes = calendar.get(Calendar.MINUTE)
        am_pm = if (calendar.get(Calendar.AM_PM) != 1) "AM" else "PM"
    }

    fun getDate() = "${day format TWO_DEC}/${month format TWO_DEC}/$year"
    fun getShortDate() = "$day ${getMonth(month)}"
    fun getTime() = "${hours format TWO_DEC}:${minutes format TWO_DEC} $am_pm"

    private fun getMonth(month: Int) = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec").get(month - 1)
    private infix fun Int.format(format: String) = String.format(format, this)
}