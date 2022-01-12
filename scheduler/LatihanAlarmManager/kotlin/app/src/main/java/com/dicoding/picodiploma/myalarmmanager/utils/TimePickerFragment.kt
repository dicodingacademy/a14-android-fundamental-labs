package com.dicoding.picodiploma.myalarmmanager.utils


import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var mListener: DialogTimeListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as DialogTimeListener?
    }

    override fun onDetach() {
        super.onDetach()
        if (mListener != null) {
            mListener = null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()
        // Jam hari ini
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        // Menit dari jam hari ini
        val minute = calendar.get(Calendar.MINUTE)

        // True untuk format 24 jam, false untuk format 12 jam (am / pm)
        val formatHour24 = true

        return TimePickerDialog(activity, this, hour, minute, formatHour24)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        mListener?.onDialogTimeSet(tag, hourOfDay, minute)
    }

    interface DialogTimeListener {
        fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int)
    }
}
