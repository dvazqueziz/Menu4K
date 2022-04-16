package com.example.menu4k.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(val listener: (year :Int, month: Int, day: Int)-> Unit) : DialogFragment(), DatePickerDialog.OnDateSetListener{
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calentario= Calendar.getInstance()
        val day: Int= calentario.get(Calendar.DAY_OF_MONTH)
        val month: Int= calentario.get(Calendar.MONTH)
        val year: Int= calentario.get(Calendar.YEAR)
        val pickerDialog = DatePickerDialog(activity as Context,this,year,month,day)
        return pickerDialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        listener(year, month+1, day)
    }
}