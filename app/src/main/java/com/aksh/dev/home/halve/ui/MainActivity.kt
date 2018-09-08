package com.aksh.dev.home.halve.ui

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aksh.dev.home.halve.R
import com.aksh.dev.home.halve.SelectorListener
import com.aksh.dev.home.halve.data.expense.Expense
import com.aksh.dev.home.halve.data.expense.ExpenseListAdapter
import com.aksh.dev.home.halve.data.expense.ExpenseType
import com.aksh.dev.home.halve.extras.DELETE_BACKGROUND
import com.aksh.dev.home.halve.extras.REMOVE_EXPENSE_ITEM
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_new_expense.*
import kotlinx.android.synthetic.main.date_layout.*
import java.util.*

class MainActivity : AppCompatActivity(), SelectorListener {
    private lateinit var viewmodel: MainViewModel
    private lateinit var allExpenseListAdapter: ExpenseListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        allExpenseListAdapter = ExpenseListAdapter()

        allExpensesList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = allExpenseListAdapter
        }

        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_round_menu, theme)
        toolbar.setNavigationOnClickListener {
            val drawer = Drawer.newInstance()
            drawer.show(supportFragmentManager, "drawer")
        }

        changeView.setOnClickListener {
            val datePickerDialog = DatePickerFragment()
            datePickerDialog.show(supportFragmentManager, "date_picker")
        }

        addNewAmount.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val typeSelector = TypeSelector()
                    typeSelector.show(supportFragmentManager, "expenseTypeSelector")
                    true
                }
                else -> false
            }
        }

        date.text = viewmodel.getShortDate()

        loadExpenses(viewmodel.getDate())

        val itemSwipeListener = ItemTouchHelper(itemSwipeListener())
        itemSwipeListener.attachToRecyclerView(allExpensesList)
    }

    private fun itemSwipeListener(): ItemTouchHelper.SimpleCallback {
        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete)
        val intrinsicWidth = deleteIcon?.intrinsicWidth ?: 0
        val intrinsicHeight = deleteIcon?.intrinsicHeight ?: 0
        val background = ColorDrawable()
        val backgroundColor = Color.parseColor(DELETE_BACKGROUND)
        val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    val expense = allExpenseListAdapter.getExpense(position)

                    if (expense != null) {
                        viewmodel.expenseRepository.removeItem(expense)

                        Snackbar.make(allExpensesList, REMOVE_EXPENSE_ITEM, Snackbar.LENGTH_LONG)
                                .setAction("UNDO") {
                                    viewmodel.expenseRepository.addItem(expense)
                                }.show()
                    }

                }
            }

            override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top
                val isCanceled = dX == 0f && !isCurrentlyActive

                if (isCanceled) {
                    canvas.drawRect(itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat(), clearPaint)
                    super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    return
                }

                background.color = backgroundColor
                background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                background.draw(canvas)

                val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
                val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
                val deleteIconRight = itemView.right - deleteIconMargin
                val deleteIconBottom = deleteIconTop + intrinsicHeight

                deleteIcon?.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
                deleteIcon?.draw(canvas)

                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                if (viewHolder.adapterPosition == 10) return 0
                return super.getMovementFlags(recyclerView, viewHolder)
            }
        }
    }

    private fun showSoftKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view.requestFocus()) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onTypeSelected(expenseType: String) {
        val title = addNewTitle.text.toString().capitalize()
        val amount = addNewAmount.text.toString().toDouble()
        val expense = Expense(date = viewmodel.getDate(), title = title, amount = amount, type = expenseType)

        viewmodel.expenseRepository.addItem(expense)

        addNewTitle.apply {
            text = null
            clearFocus()
        }
        addNewAmount.text = null
        addNewAmount.clearFocus()

        newItemContainer.visibility = View.GONE
        hideSoftKeyboard(addNewTitle)

        viewmodel.addNewDialogVisible = false
    }

    override fun onDateSelected(selectedDate: String) {
        viewmodel.getAllExpensesByDate(selectedDate).observe(this, Observer<List<Expense>> { list ->
            allExpenseListAdapter.allExpenses = list
            viewmodel.amountSpend = 0.0
            viewmodel.amountSaved = 0.0
            list.forEach { expense ->
                when (expense.type) {
                    ExpenseType.SAVINGS -> viewmodel.amountSaved += expense.amount
                    else -> viewmodel.amountSpend += expense.amount
                }
            }

            amountSpend.text = getString(R.string.amount_spend).format(viewmodel.amountSpend.toString())
            amountSaved.text = getString(R.string.amount_spend).format(viewmodel.amountSaved.toString())
        })

        date.text = getShortDate(selectedDate)
        dayName.text = viewmodel.getDayName(selectedDate)
    }

    private fun loadExpenses(date: String) = onDateSelected(date)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.all_expenses_trailing, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        amountSpend.text = getString(R.string.amount_spend).format(viewmodel.amountSpend.toString())
        amountSaved.text = getString(R.string.amount_spend).format(viewmodel.amountSaved.toString())

        viewmodel.setCalendar(Calendar.getInstance())
        loadExpenses(viewmodel.getDate())
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        invalidateOptionsMenu()
        if (menu != null) {
            menu.findItem(R.id.addExpenseDialog).isVisible = !viewmodel.addNewDialogVisible
            menu.findItem(R.id.closeExpenseDialog).isVisible = viewmodel.addNewDialogVisible
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.addExpenseDialog -> {
                newItemContainer.visibility = View.VISIBLE
                addNewTitle.apply {
                    requestFocus()
                }
                showSoftKeyboard(addNewTitle)
                viewmodel.addNewDialogVisible = true
            }
            R.id.closeExpenseDialog -> {
                newItemContainer.visibility = View.GONE
                hideSoftKeyboard(addNewTitle)
                viewmodel.addNewDialogVisible = false
            }
        }
        return true
    }

    private fun getShortDate(date: String): String {
        val arr = date.split("/")
        return "${arr[0]} ${getMonth(arr[1].toInt())}"
    }

    private fun getMonth(month: Int) = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec").get(month - 1)
}
