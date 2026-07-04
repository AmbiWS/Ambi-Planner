package com.ambiws.ambiplanner.features.home.ui.routine

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.ambiws.ambiplanner.base.BaseFragment
import com.ambiws.ambiplanner.databinding.FragmentEditRoutineBinding
import com.ambiws.ambiplanner.features.home.ui.routine.model.RoutineViewData
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Locale

class EditRoutineItemFragment : BaseFragment<EditRoutineItemViewModel, FragmentEditRoutineBinding>(
    FragmentEditRoutineBinding::inflate
) {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                binding.cbNotification.isChecked = false
                Toast.makeText(requireContext(), "Permission denied for notifications", Toast.LENGTH_SHORT).show()
            }
        }

    private val args: EditRoutineItemFragmentArgs by lazy {
        EditRoutineItemFragmentArgs.fromBundle(requireArguments())
    }

    var startTime : String? = null
    var timeToComplete : String? = null

    override fun setupUi() {
        super.setupUi()
        binding.tvTitle.text = if (args.routine == null) "New Routine" else {
            binding.etTitle.setText(args.routine?.title)
            binding.etDescription.setText(args.routine?.description)
            binding.cbEnableTime.isChecked = args.routine?.startTime != null
            binding.cbEstimateTime.isChecked = args.routine?.timeToComplete != null
            binding.cbNotification.isChecked = args.routine?.isNotificationEnabled == true
            startTime = args.routine?.startTime
            timeToComplete = args.routine?.timeToComplete
            "Edit Routine"
        }
        binding.tvTitle.text = if (args.routine == null) "New Routine" else "Edit Routine"
        binding.btnDelete.isVisible = args.routine != null
    }

    override fun setupListeners() {
        super.setupListeners()

        val pickerStart =
            MaterialTimePicker.Builder()
                .setTheme(com.ambiws.ambiplanner.R.style.ThemeOverlay_App_TimePicker)
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Routine Time")
                .build()

        val pickerTimeToComplete =
            MaterialTimePicker.Builder()
                .setTheme(com.ambiws.ambiplanner.R.style.ThemeOverlay_App_TimePicker)
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(1)
                .setMinute(0)
                .setTitleText("Select Time To Complete")
                .build()

        pickerStart.addOnPositiveButtonClickListener {
            startTime = "${String.Companion.format(Locale.ROOT, "%02d", pickerStart.hour)}:${
                String.Companion.format(
                    Locale.ROOT, "%02d", pickerStart.minute)}"
            binding.tvStartTime.text = startTime
        }

        pickerStart.addOnNegativeButtonClickListener {
            binding.cbEnableTime.isChecked = false
            startTime = null
            binding.tvStartTime.text = ""
        }

        pickerStart.addOnCancelListener {
            binding.cbEnableTime.isChecked = false
            startTime = null
            binding.tvStartTime.text = ""
        }

        pickerTimeToComplete.addOnPositiveButtonClickListener {
            timeToComplete = "${pickerTimeToComplete.hour}h ${pickerTimeToComplete.minute}m"
            binding.tvEstimateTime.text = timeToComplete
        }

        pickerTimeToComplete.addOnNegativeButtonClickListener {
            binding.cbEstimateTime.isChecked = false
            timeToComplete = null
            binding.tvEstimateTime.text = ""
        }

        pickerTimeToComplete.addOnCancelListener {
            binding.cbEstimateTime.isChecked = false
            timeToComplete = null
            binding.tvEstimateTime.text = ""
        }

        binding.cbEnableTime.setOnCheckedChangeListener { _, bool ->
            if (bool) {
                pickerStart.show(childFragmentManager, "TimePickerStart")
            } else {
                startTime = null
                binding.tvStartTime.text = ""
            }
        }

        binding.cbEstimateTime.setOnCheckedChangeListener { _, bool ->
            if (bool) {
                pickerTimeToComplete.show(childFragmentManager, "TimePickerEstimate")
            } else {
                timeToComplete = null
                binding.tvEstimateTime.text = ""
            }
        }

        binding.cbNotification.setOnCheckedChangeListener { _, bool ->
            if (bool) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }
        }

        binding.btnSave.setOnClickListener {
            if (binding.etTitle.text.isNullOrBlank()) {
                Snackbar.make(binding.root, "Title is required", Snackbar.LENGTH_LONG).show()
            } else {
                val id = if (args.routine == null) 0 else args.routine?.id
                viewModel.saveRoutine(
                    RoutineViewData(
                        id = id,
                        title = binding.etTitle.text.toString(),
                        description = binding.etDescription.text?.toString()?.takeIf { it.isNotBlank() },
                        startTime = startTime,
                        timeToComplete = timeToComplete,
                        isNotificationEnabled = binding.cbNotification.isChecked
                    )
                )
            }
        }

        binding.ivBack.setOnClickListener {
            viewModel.navigateBack()
        }
        
        binding.btnDelete.setOnClickListener {
            args.routine?.let {
                viewModel.deleteRoutine(it)
            } ?: run {
                Snackbar.make(binding.root, "Routine is null", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
