package com.aksh.dev.home.halve.data.expense

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ExpenseRepository(application: Application) {
    private val database: ExpensesDatabase = ExpensesDatabase.getDatabase(application)
    private val expenseDao: ExpenseDao = database.expenseDao()

    val allItems: LiveData<List<Expense>> = expenseDao.allExpenses
    fun allExpensesByDate(date: String) = expenseDao.expensesByDate(date)

    fun addItem(expense: Expense) {
        AddExpense(expenseDao).execute(expense)
    }

    fun removeItem(expense: Expense) {
        RemoveExpense(expenseDao).execute(expense)
    }

    fun updateItem(expense: Expense) {
        UpdateExpense(expenseDao).execute(expense)
    }

    class AddExpense(private val expenseDao: ExpenseDao) : AsyncTask<Expense, Unit, Unit>() {
        override fun doInBackground(vararg expenses: Expense) {
            expenseDao.addExpense(expense = expenses[0])
        }
    }

    class RemoveExpense(private val expenseDao: ExpenseDao) : AsyncTask<Expense, Unit, Unit>() {
        override fun doInBackground(vararg expenses: Expense) {
            expenseDao.deleteExpense(expense = expenses[0])
        }
    }

    class UpdateExpense(private val expenseDao: ExpenseDao) : AsyncTask<Expense, Unit, Unit>() {
        override fun doInBackground(vararg expenses: Expense) {
            expenseDao.updateExpense(expense = expenses[0])
        }
    }
}