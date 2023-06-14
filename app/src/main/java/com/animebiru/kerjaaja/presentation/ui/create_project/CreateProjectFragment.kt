package com.animebiru.kerjaaja.presentation.ui.create_project

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentCreateProjectBinding
import com.animebiru.kerjaaja.domain.events.ProjectEvent
import com.animebiru.kerjaaja.domain.models.ProjectCategory
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.collectFlowWhenStarted
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.collectLatestFlowWhenStarted
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.indexesOf
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.listener.OnDialogPositiveButtonClickListener
import com.animebiru.kerjaaja.presentation.ui.activity.maps.MapsActivity
import com.animebiru.kerjaaja.presentation.ui.dialog.DialogActionSuccess
import com.animebiru.kerjaaja.presentation.ui.dialog.DialogSimple
import com.animebiru.kerjaaja.presentation.viewmodels.CreateProjectViewModel
import com.animebiru.kerjaaja.presentation.viewmodels.ProjectViewModel
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class CreateProjectFragment : Fragment(R.layout.fragment_create_project) {

    private val binding by viewBindings(FragmentCreateProjectBinding::bind)
    private val projectViewModel: ProjectViewModel by viewModels()
    private val createProjectViewModel: CreateProjectViewModel by viewModels()
    private val appBarConfiguration by lazy { AppBarConfiguration(setOf(R.id.homeFragment, R.id.historyFragment, R.id.profileFragment)) }
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var mapsActivityLauncher: ActivityResultLauncher<Intent>
    private val datePicker by lazy {
        MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setTitleText("Deadline project anda").build()
    }
    private val dialogProjectCreated by lazy {
        DialogActionSuccess().apply {
            message = "Project berhasil dibuat"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), permissionsResultCallback)
        mapsActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), mapsActivityResultCallback)

        binding.mtbCreateJobPage.setupWithNavController(findNavController(), appBarConfiguration)

        collectFlowWhenStarted(projectViewModel.projectEvent) {
            when (it) {
                is ProjectEvent.OnNewProjectCreated -> {
                    dialogProjectCreated.show(parentFragmentManager, null)
                    findNavController().popBackStack()
                }
                else -> Unit
            }
        }

        collectLatestFlowWhenStarted(projectViewModel.projectCategories) {
            setDropdownCategoryList(it)
        }

        binding.acTvCategory.addTextChangedListener(acTvCategoryTextWatcher)
        binding.etlJobLocation.setEndIconOnClickListener {
            if (MapsActivity.REQUIRED_PERMISSION.all { ActivityCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED }) {
                mapsActivityLauncher.launch(Intent(requireActivity(), MapsActivity::class.java))
            } else {
                locationPermissionLauncher.launch(MapsActivity.REQUIRED_PERMISSION.toTypedArray())
            }
        }
        binding.etlExpire.setEndIconOnClickListener {
            datePicker.show(parentFragmentManager, null)
            datePicker.addOnPositiveButtonClickListener {
                val date = Date(it)
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val formattedDate = formatter.format(date)
                createProjectViewModel.setCurrentDateInput(formattedDate)
            }
        }
        createProjectViewModel.currentLocationInput.observe(viewLifecycleOwner) {
            binding.etJobLocation.setText(it)
        }
        createProjectViewModel.currentDateInput.observe(viewLifecycleOwner) {
            SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(it)?.let { date ->
                val readableDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
                binding.etExpire.setText(readableDate)
            }
        }
        binding.mtbCreateJobPage.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuCreate -> {
                    val description = binding.etJobDescription.text.toString()
                    val projectFee = binding.etProjectFee.text.toString()
                    val expire = createProjectViewModel.currentDateInput.value
                    val location = createProjectViewModel.currentLocationInput.value
                    val projectCategories = binding.acTvCategory.text.toString()
                    projectViewModel.createProject(description, projectFee, expire, location, projectCategories)
                    true
                }
                else -> false
            }
        }
    }

    private val mapsActivityResultCallback = ActivityResultCallback<ActivityResult> { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                val location = intent.getStringExtra(MapsActivity.EXTRA_LOCATION)
                location?.let { createProjectViewModel.setCurrentLocationInput(it) }
            }
        }
    }

    private val permissionsResultCallback = ActivityResultCallback<Map<String, Boolean>> { result ->
        if (result.all { ActivityCompat.checkSelfPermission(requireContext(), it.key) == PackageManager.PERMISSION_GRANTED }) {
            mapsActivityLauncher.launch(Intent(requireActivity(), MapsActivity::class.java)).also { return@ActivityResultCallback }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)  {
            if (result.any { shouldShowRequestPermissionRationale(it.key) }) {
                dialogPermissionLocationRationale.onPositiveButtonClickListener = OnDialogPositiveButtonClickListener {
                    locationPermissionLauncher.launch(MapsActivity.REQUIRED_PERMISSION.toTypedArray())
                    return@OnDialogPositiveButtonClickListener
                }
                dialogPermissionLocationRationale.show(parentFragmentManager, null)
            }
        }
        dialogPermissionNotGranted.show(parentFragmentManager, null)
    }

    private val acTvCategoryTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(s: Editable?) {
            val text = s?.toString() ?: return
            s.getSpans(0, s.length, ImageSpan::class.java).forEach { s.removeSpan(it) }
            val categoriesFromInput = text.split(",", ignoreCase = true).map { it.trim() }
            categoriesFromInput.forEach { input ->
                if (projectViewModel.projectCategories.value.map { it.title.lowercase() }.any { it == input.lowercase() } ) {
                    val chipDrawable = ChipDrawable.createFromResource(requireContext(), R.xml.standalone_chip)
                    chipDrawable.text = input
                    chipDrawable.setBounds(0,0, chipDrawable.intrinsicWidth, chipDrawable.intrinsicHeight)
                    val span = ImageSpan(chipDrawable)
                    val startIdx = text.indexesOf(input)
                    if (startIdx.isNotEmpty()) {
                        s.setSpan(span, startIdx[0], startIdx[0] + input.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                }
            }
        }
    }

    private fun setDropdownCategoryList(projectCategories: List<ProjectCategory>) {
        val arrayString = projectCategories.map { it.title }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayString)
        binding.acTvCategory.setAdapter(adapter)
        binding.acTvCategory.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
    }

    private val dialogPermissionLocationRationale by lazy { DialogSimple().apply {
        title = "Kami perlu izin anda"
        message = "Kami perlu izin untutk mengakses lokasi device anda agar dapat menampilkan map"
    } }

    private val dialogPermissionNotGranted by lazy { DialogSimple().apply {
        title = "Aplikasi tidak memiliki izin"
        message = "Kami tidak dapat menampilkan map karena ada tidak memberikan izin akses lokasi device anda, mohon ubah perizinan melalui settings"
        positiveButtonLabel = "Settings"
        onPositiveButtonClickListener = OnDialogPositiveButtonClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.fromParts("package", this@CreateProjectFragment.requireContext().packageName, null)
            startActivity(intent)
        }
    } }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.acTvCategory.removeTextChangedListener(acTvCategoryTextWatcher)
    }

}