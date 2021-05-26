package net.eucalypto.criminalintent.crimedetail

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_KEY_DATE = "date"
const val RESULT_KEY_DATE = "result_key_date"

class DatePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_KEY_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val resultDate = GregorianCalendar(year, month, dayOfMonth).time

            val bundle = Bundle().apply { putSerializable(RESULT_KEY_DATE, resultDate) }
            parentFragmentManager.setFragmentResult(REQUEST_KEY_DATE, bundle)
        }

        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }

    companion object {
        fun newInstance(date: Date): DatePickerFragment {
            val arguments = Bundle().apply {
                putSerializable(ARG_KEY_DATE, date)
            }

            return DatePickerFragment().apply {
                this.arguments = arguments
            }
        }
    }
}