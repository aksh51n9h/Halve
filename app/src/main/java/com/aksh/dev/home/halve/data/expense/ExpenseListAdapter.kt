package com.aksh.dev.home.halve.data.expense

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aksh.dev.home.halve.R
import kotlinx.android.synthetic.main.expense_item.view.*

class ExpenseListAdapter : RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder>() {
    var allExpenses: List<Expense>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        return ExpenseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.expense_item, parent, false))
    }

    override fun getItemCount() = allExpenses?.size ?: 0

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        if (allExpenses != null) {
            val expense = allExpenses?.get(position)
            if (expense != null) {
                holder.title.text = expense.title
                holder.type.text = expense.type
                holder.amount.text = "\u20b9 ${expense.amount}"

                val imgResID = when (expense.type) {
                    ExpenseType.FOOD -> R.drawable.ic_round_fastfood
                    ExpenseType.TRANSPORTATION -> R.drawable.ic_round_car
                    ExpenseType.BILLS -> R.drawable.ic_round_receipt
                    ExpenseType.GIFTS -> R.drawable.ic_round_card_giftcard
                    ExpenseType.SHOPPING -> R.drawable.ic_round_shopping_basket
                    ExpenseType.CLOTHING -> R.drawable.ic_t_shirt
                    ExpenseType.MEDICAL -> R.drawable.ic_doctor_stethoscope
                    ExpenseType.FITNESS -> R.drawable.ic_round_fitness_center
                    ExpenseType.ENTERTAINMENT -> R.drawable.ic_round_movie
                    ExpenseType.MOVIES -> R.drawable.ic_round_theaters
                    ExpenseType.SAVINGS -> R.drawable.ic_round_account_balance_wallet
                    ExpenseType.GROCERY -> R.drawable.ic_groceries
                    ExpenseType.MOBILE -> R.drawable.ic_round_phone_android
                    ExpenseType.PAYMENTS -> R.drawable.ic_round_credit_card

                    else -> R.drawable.ic_round_add
                }

                val textColor = when (expense.type) {
                    ExpenseType.FOOD -> ExpenseTypeColors.FOOD
                    ExpenseType.TRANSPORTATION, ExpenseType.CLOTHING, ExpenseType.ENTERTAINMENT -> ExpenseTypeColors.TRANSPORTATION
                    ExpenseType.BILLS -> ExpenseTypeColors.BILLS
                    ExpenseType.FITNESS, ExpenseType.MOBILE, ExpenseType.PAYMENTS -> ExpenseTypeColors.FITNESS
                    ExpenseType.SAVINGS, ExpenseType.GROCERY -> ExpenseTypeColors.SAVINGS
                    ExpenseType.MEDICAL, ExpenseType.MOVIES -> ExpenseTypeColors.MOVIES
                    ExpenseType.GIFTS -> ExpenseTypeColors.GIFTS
                    ExpenseType.SHOPPING -> ExpenseTypeColors.SHOPPING
                    else -> ExpenseTypeColors.OTHERS
                }

                holder.type.setTextColor(Color.parseColor(textColor))
                holder.icon.setImageResource(imgResID)
            }
        }
    }

    fun getExpense(position: Int) = allExpenses?.get(position)
    inner class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.expenseItemIcon
        val title: TextView = view.expenseItemTitle
        val type: TextView = view.expenseItemType
        val amount: TextView = view.expenseItemAmount
    }
}