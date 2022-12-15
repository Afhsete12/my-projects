package com.app.alphavlaka.adapters

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerAdapter (private val listener: (String) -> Unit) : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {
    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        listener("$p1:$p2")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        return TimePickerDialog(context, this, hour, minute, false)
    }

}