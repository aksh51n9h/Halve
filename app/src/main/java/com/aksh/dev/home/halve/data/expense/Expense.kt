package com.aksh.dev.home.halve.data.expense

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "all_expenses")
data class Expense(
        @PrimaryKey(autoGenerate = true) val i: Int = 0,
        var title: String,
        var date: String,
        var type: String,
        var amount: Double
)