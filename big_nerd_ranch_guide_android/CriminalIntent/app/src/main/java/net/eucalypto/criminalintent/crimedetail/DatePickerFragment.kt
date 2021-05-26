package net.eucalypto.criminalintent.crimedetail

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_KEY_DATE = "date"
private const val ARG_KEY_RESULT_REQUEST_KEY = "arg_key_request_key"
private const val RESULT_KEY_DATE = "result_key_date"

class DatePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val resultRequestKey = arguments?.getString(ARG_KEY_RESULT_REQUEST_KEY)!!
        val initialDate = arguments?.getSerializable(ARG_KEY_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = initialDate
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val resultDate = GregorianCalendar(year, month, dayOfMonth).time

            val bundle = Bundle().apply { putSerializable(RESULT_KEY_DATE, resultDate) }
            parentFragmentManager.setFragmentResult(resultRequestKey, bundle)
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
        fun newInstance(initialDate: Date, requestKey: String): DatePickerFragment {
            val arguments = Bundle().apply {
                putSerializable(ARG_KEY_DATE, initialDate)
                putString(ARG_KEY_RESULT_REQUEST_KEY, requestKey)
            }

            return DatePickerFragment().apply {
                this.arguments = arguments
            }
        }

        fun getDateFrom(bundle: Bundle): Date {
            return bundle.getSerializable(RESULT_KEY_DATE) as Date
        }
    }
}