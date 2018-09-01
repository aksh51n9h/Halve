package com.aksh.dev.home.halve.data.expense

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addExpense(expense: Expense)

    @Delete
    fun deleteExpense(expense: Expense)

    @Update
    fun updateExpense(expense: Expense)

    @get:Query("SELECT * FROM all_expenses ORDER BY date ASC")
    val allExpenses: LiveData<List<Expense>>

    @Query("SELECT * FROM all_expenses WHERE date=:date")
    fun expensesByDate(date: String): LiveData<List<Expense>>
}