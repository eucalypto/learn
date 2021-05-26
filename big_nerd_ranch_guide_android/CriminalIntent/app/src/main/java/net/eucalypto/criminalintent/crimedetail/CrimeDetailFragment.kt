package net.eucalypto.criminalintent.crimedetail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.eucalypto.criminalintent.Crime
import net.eucalypto.criminalintent.R
import timber.log.Timber

private const val REQUEST_KEY_DATE = "date_request_key"

class CrimeDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crime, container, false)
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(CrimeDetailViewModel::class.java)
    }
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox
    private lateinit var crime: Crime
    private val args: CrimeDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val crimeId = args.crimeId
        Timber.d("Args gave crimeId: $crimeId")
        viewModel.loadCrime(crimeId)

        getReferencesToViews(view)
        setCrimeLiveDataObserver()
        setDateButtonClickListener()
        setDatePickerResultListener()
    }

    private fun getReferencesToViews(view: View) {
        titleField = view.findViewById(R.id.crime_title)
        dateButton = view.findViewById(R.id.crime_date)
        solvedCheckBox = view.findViewById(R.id.crime_solved)
    }

    private fun setCrimeLiveDataObserver() {
        viewModel.crimeLiveData.observe(viewLifecycleOwner) { crime ->
            crime?.let {
                this.crime = crime
                updateUI()
            }
        }
    }

    private fun setDateButtonClickListener() {
        dateButton.setOnClickListener {
            navigateToDatePickerDialog()
        }
    }

    private fun navigateToDatePickerDialog() {
        val action =
            CrimeDetailFragmentDirections
                .actionCrimeDetailFragmentToDatePickerFragment(
                    crime.date,
                    REQUEST_KEY_DATE
                )
        val navController = parentFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container)!!
            .findNavController()
        navController.navigate(action)
    }

    private fun setDatePickerResultListener() {
        parentFragmentManager.setFragmentResultListener(
            REQUEST_KEY_DATE,
            viewLifecycleOwner
        ) { _, bundle ->
            crime.date = DatePickerFragment.getDateFrom(bundle)
            updateUI()
        }
    }

    private fun updateUI() {
        titleField.setText(crime.title)
        dateButton.text = crime.date.toString()
        solvedCheckBox.apply {
            isChecked = crime.isSolved
            jumpDrawablesToCurrentState()
        }
    }

    override fun onStart() {
        super.onStart()

        titleField.addTextChangedListener(createTitleWatcher())
        solvedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            crime.isSolved = isChecked
        }
    }

    override fun onStop() {
        super.onStop()
        Timber.d("Update crime in database: $crime")
        viewModel.saveCrime(crime)
    }

    private fun createTitleWatcher(): TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Not needed
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            crime.title = s.toString()
        }

        override fun afterTextChanged(s: Editable?) {
            // Not needed
        }

    }
}