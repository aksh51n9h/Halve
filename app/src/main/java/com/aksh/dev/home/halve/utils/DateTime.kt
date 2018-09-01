package com.aksh.dev.home.halve.utils

import com.aksh.dev.home.halve.extras.TWO_DEC
import java.util.*

class DateTime(private val calendar: Calendar) {
    val dayInMonth = calendar.get(Calendar.DAY_OF_MONTH)
    val dayInWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val month = calendar.get(Calendar.MONTH) + 1
    val year = calendar.get(Calendar.YEAR)
    val hours = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val second = calendar.get(Calendar.SECOND)

    fun getDate() = "${dayInMonth.fix(TWO_DEC)}/${month.fix(TWO_DEC)}/$year"
    fun getShortDate() = "${dayInMonth.fix(TWO_DEC)} ${getMonth(month)}"
    fun getDayName() = getDay(dayInWeek)
    fun getDayName(date: String): String {
        val dateArray = date.split("/")
        val calendar = Calendar.getInstance().apply {
            set(dateArray[2].toInt(), dateArray[1].toInt().plus(-1), dateArray[0].toInt())
        }

        val newDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val newDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        return if (dayInMonth != newDayOfMonth && dayInWeek != newDayOfWeek) {
            getDay(newDayOfWeek)
        } else {
            "Today"
        }
    }

    private fun getDay(dayInWeek: Int): String {
        return when (dayInWeek) {
            Calendar.MONDAY -> "Mon"
            Calendar.TUESDAY -> "Tue"
            Calendar.WEDNESDAY -> "Wed"
            Calendar.THURSDAY -> "Thu"
            Calendar.FRIDAY -> "Fri"
            Calendar.SATURDAY -> "Sat"
            Calendar.SUNDAY -> "Sun"
            else -> "Today"
        }
    }

    private fun getMonth(month: Int): String {
        return arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")[month - 1]
    }

    private infix fun Int.fix(format: String) = String.format(format, this)
}