package com.ambiws.ambiplanner.features.home.routine.ui

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

        pickerStart.addOnPositiveButtonClickListener {
            startTime = "${String.format(Locale.ROOT, "%02d", pickerStart.hour)}:${String.format(Locale.ROOT, "%02d", pickerStart.minute)}"
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

        binding.cbEnableTime.setOnCheckedChangeListener { _, bool ->
            if (bool) {
                pickerStart.show(childFragmentManager, "TimePicker")
            } else {
                startTime = null
                binding.tvStartTime.text = ""
            }
        }
    }
}
