package com.aksh.dev.home.halve

interface SelectorListener {
    fun onTypeSelected(expenseType: String)
    fun onDateSelected(selectedDate: String)
}