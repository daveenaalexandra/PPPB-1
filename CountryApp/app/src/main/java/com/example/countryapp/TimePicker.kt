package com.example.countryapp

import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.util.Calendar


import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment


class TimePicker: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog (
            requireActivity(),
            activity as TimePickerDialog.OnTimeSetListener,
            hour,
            minute,
            DateFormat.is24HourFormat(activity)

        )
    }
}