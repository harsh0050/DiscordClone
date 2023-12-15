package com.harsh.discordclone.util

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment(private val preset: Calendar) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private var _callback: CustomDataCallback<Calendar>? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = preset.get(Calendar.YEAR)
        val month = preset.get(Calendar.MONTH)
        val day = preset.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireActivity(), this, year, month, day)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

        return datePickerDialog
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.set(year, month, dayOfMonth)
        _callback?.onCallback(selectedCalendar)
    }
    fun setOnDateSetListener(listener: CustomDataCallback<Calendar>): DatePickerFragment {
        _callback = listener
        return this
    }

}