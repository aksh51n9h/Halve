package com.aksh.dev.home.halve.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.aksh.dev.home.halve.data.expense.ExpenseRepository
import com.aksh.dev.home.halve.utils.DateTime
import java.util.*

class MainViewModel(application: Application):AndroidViewModel(application){
    val expenseRepository = ExpenseRepository(application)
    private var calendar = DateTime(Calendar.getInstance())

    var addNewDialogVisible = false
    var amountSpend = 0.0
    var amountSaved = 0.0


    fun setCalendar(calendar: Calendar) {
        this.calendar = DateTime(calendar)
    }

    fun getAllExpensesByDate(date: String) = expenseRepository.allExpensesByDate(date)
    fun getDate() = calendar.getDate()
    fun getShortDate() = calendar.getShortDate()
    fun getDayName() = calendar.getDayName()
    fun getDayName(date: String) = calendar.getDayName(date)
}