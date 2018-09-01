package com.aksh.dev.home.halve.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.aksh.dev.home.halve.SelectorListener
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var selectorListener: SelectorListener
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val date = "${day.format("%02d")}/${month.plus(1).format("%02d")}/$year"
        selectorListener.onDateSelected(date)
        dismiss()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        selectorListener = activity as SelectorListener
    }

    private infix fun Int.format(format: String) = String.format(format, this)
}