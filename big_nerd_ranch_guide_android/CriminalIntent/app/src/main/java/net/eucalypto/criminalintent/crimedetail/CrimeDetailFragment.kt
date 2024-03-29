package net.eucalypto.criminalintent.crimedetail

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.eucalypto.criminalintent.Crime
import net.eucalypto.criminalintent.R
import net.eucalypto.criminalintent.getScaledBitmap
import timber.log.Timber
import java.io.File

private const val REQUEST_KEY_DATE = "date_request_key"
private const val REQUEST_PHOTO = 2
private const val DATE_FORMAT = "EEE, MMM, dd"

class CrimeDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crime_detail, container, false)
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(CrimeDetailViewModel::class.java)
    }
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox
    private lateinit var crimeReportButton: Button
    private lateinit var suspectButton: Button
    private lateinit var photoButton: ImageButton
    private lateinit var photoView: ImageView
    private lateinit var crime: Crime
    private val args: CrimeDetailFragmentArgs by navArgs()
    private lateinit var photoFile: File
    private lateinit var photoUri: Uri

    private val getContactNameActivityLauncher =
        registerForActivityResult(ActivityResultContracts.PickContact()) { uri ->
            processNameFromContactApp(uri)
        }

    private fun processNameFromContactApp(uri: Uri) {
        val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
        val cursor = activity?.contentResolver?.query(
            uri,
            queryFields,
            null,
            null,
            null
        )
        cursor?.use {
            if (it.count == 0) return@use

            it.moveToFirst()
            val suspect = it.getString(0)
            crime.suspect = suspect
            viewModel.saveCrime(crime)
            suspectButton.text = suspect
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val crimeId = args.crimeId
        Timber.d("Args gave crimeId: $crimeId")
        viewModel.loadCrime(crimeId)

        getReferencesToViews(view)
        setCrimeLiveDataObserver()
        setDateButtonClickListener()
        setDatePickerResultListener()
        setCrimeReportButtonListener()
        setUpSuspectButton()
        setUpPhotoButton()
    }

    private fun setUpPhotoButton() {
        photoButton.apply {
            val packageManager = requireActivity().packageManager
            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity =
                packageManager.resolveActivity(captureImage, PackageManager.MATCH_DEFAULT_ONLY)
            if (resolvedActivity == null) isEnabled = false

            setOnClickListener {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

                val cameraActivities: List<ResolveInfo> = packageManager.queryIntentActivities(
                    captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
                for (cameraActivity in cameraActivities) {
                    requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }

                startActivityForResult(captureImage, REQUEST_PHOTO)
            }
        }
    }

    private fun getReferencesToViews(view: View) {
        titleField = view.findViewById(R.id.crime_title)
        dateButton = view.findViewById(R.id.crime_date)
        solvedCheckBox = view.findViewById(R.id.crime_solved)
        crimeReportButton = view.findViewById(R.id.crime_report)
        suspectButton = view.findViewById(R.id.crime_suspect)
        photoButton = view.findViewById(R.id.crime_camera)
        photoView = view.findViewById(R.id.crime_photo)
    }

    private fun setCrimeLiveDataObserver() {
        viewModel.crimeLiveData.observe(viewLifecycleOwner) { crime ->
            crime?.let {
                this.crime = crime
                photoFile = viewModel.getPhotoFile(crime)
                photoUri = FileProvider.getUriForFile(
                    requireActivity(),
                    "net.eucalypto.criminalintent.fileprovider",
                    photoFile
                )
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

    private fun setCrimeReportButtonListener() {
        crimeReportButton.setOnClickListener {
            Intent(Intent.ACTION_SEND)
                .apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, getCrimeReport())
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject))
                }.also {
                    startActivity(it)
                }
        }
    }

    private fun setUpSuspectButton() {
        suspectButton.apply {
            setOnClickListener {
                getContactNameActivityLauncher.launch()
            }

            disableIfNoContactsActivityFound(this)
        }
    }

    private fun disableIfNoContactsActivityFound(button: Button) {
        val intent =
            getContactNameActivityLauncher.contract.createIntent(requireContext(), null)
        val packageManager: PackageManager = requireActivity().packageManager
        val resolvedActivity =
            packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (resolvedActivity == null) button.isEnabled = false
    }

    private fun updateUI() {
        titleField.setText(crime.title)
        dateButton.text = crime.date.toString()
        solvedCheckBox.apply {
            isChecked = crime.isSolved
            jumpDrawablesToCurrentState()
        }
        if (crime.suspect.isNotEmpty()) {
            suspectButton.text = crime.suspect
        }

        updatePhotoView()
    }

    private fun updatePhotoView() {
        if (photoFile.exists()) {
            val bitmap = getScaledBitmap(photoFile.path, requireActivity())
            photoView.setImageBitmap(bitmap)
            photoView.contentDescription = getString(R.string.crime_photo_image_description)
        } else {
            photoView.setImageDrawable(null)
            photoView.contentDescription = getString(R.string.crime_photo_no_image_description)
        }
    }

    private fun getCrimeReport(): String {
        val solvedString = if (crime.isSolved) {
            getString(R.string.crime_report_solved)
        } else {
            getString(R.string.crime_report_unsolved)
        }

        val dateString = DateFormat.format(DATE_FORMAT, crime.date).toString()

        val suspect = if (crime.suspect.isBlank()) {
            getString(R.string.crime_report_no_suspect)
        } else {
            getString(R.string.crime_report_suspect, crime.suspect)
        }
        return getString(R.string.crime_report, crime.title, dateString, solvedString, suspect)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            resultCode != Activity.RESULT_OK -> return

            requestCode == REQUEST_PHOTO -> {
                updatePhotoView()
                requireActivity().revokeUriPermission(
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
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

    override fun onDetach() {
        super.onDetach()
        requireActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
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