package com.aksh.dev.home.halve.data.expense

class ExpenseType {
    companion object {
        val CLOTHING = "Clothing"
        val BILLS = "Bills"
        val ENTERTAINMENT = "Entertainment"
        val FITNESS = "Fitness"
        val FOOD = "Food"
        val GIFTS = "Gifts"
        val MEDICAL = "Medical"
        val MOVIES = "Movies"
        val OTHERS = "Others"
        val SAVINGS = "Savings"
        val SHOPPING = "Shopping"
        val TRANSPORTATION = "Transportation"
        val GROCERY = "Grocery"
        val MOBILE = "Mobile"
        val PAYMENTS = "Payments"

        fun toArray() = arrayOf(FOOD,
                TRANSPORTATION, GROCERY, MOBILE, PAYMENTS, BILLS, GIFTS,
                SHOPPING, CLOTHING, MEDICAL,
                FITNESS, ENTERTAINMENT, MOVIES, SAVINGS, OTHERS)
    }
}