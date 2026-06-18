package com.ambiws.ambiplanner.features.home.ui.routine

import com.ambiws.ambiplanner.base.BaseFragment
import com.ambiws.ambiplanner.databinding.FragmentEditRoutineBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Locale

class EditRoutineItemFragment : BaseFragment<EditRoutineItemViewModel, FragmentEditRoutineBinding>(
    FragmentEditRoutineBinding::inflate
) {

    var startTime : String? = null
    var timeToComplete : String? = null

    override fun setupListeners() {
        super.setupListeners()

        val pickerStart =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Routine Time")
                .build()

        val pickerTimeToComplete =
            MaterialTimePicker.Builder()
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
    }
}