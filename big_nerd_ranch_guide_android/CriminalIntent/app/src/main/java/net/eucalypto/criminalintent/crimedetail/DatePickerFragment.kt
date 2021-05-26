package net.eucalypto.criminalintent.crimedetail

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import java.util.*

private const val RESULT_KEY_DATE = "result_key_date"

class DatePickerFragment : DialogFragment() {

    private val args: DatePickerFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val resultRequestKey = args.requestKey
        val initialDate = args.initialDate
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
        fun getDateFrom(bundle: Bundle): Date {
            return bundle.getSerializable(RESULT_KEY_DATE) as Date
        }
    }
}