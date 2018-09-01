package com.aksh.dev.home.halve.data.expense;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Expense.class}, exportSchema = false, version = 1)
public abstract class ExpensesDatabase extends RoomDatabase {
    private static ExpensesDatabase INSTANCE;

    public static ExpensesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExpensesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ExpensesDatabase.class, "expenses_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ExpenseDao expenseDao();
}
