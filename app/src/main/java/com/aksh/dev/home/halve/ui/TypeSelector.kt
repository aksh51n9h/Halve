package com.aksh.dev.home.halve.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.aksh.dev.home.halve.R
import com.aksh.dev.home.halve.SelectorListener
import com.aksh.dev.home.halve.data.expense.ExpenseType

class TypeSelector : DialogFragment() {
    private lateinit var selectorListener: SelectorListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val expenseType = ExpenseType.toArray()
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.select_type)
                .setItems(expenseType) { dialog, which ->
                    val type = expenseType[which]
                    selectorListener.onTypeSelected(type)
                    dialog.dismiss()
                }
        return builder.create()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        selectorListener = activity as SelectorListener
    }
}