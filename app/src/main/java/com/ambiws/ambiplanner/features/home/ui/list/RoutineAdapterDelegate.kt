package com.ambiws.ambiplanner.features.home.ui.list

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.ambiws.ambiplanner.R
import com.ambiws.ambiplanner.databinding.ItemRoutineBinding
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import java.util.Calendar
import java.util.Locale

object RoutineAdapterDelegate {

    fun routineAdapterDelegate(
        onClickListener: (RoutineItemModel) -> Unit,
        onDoneChanged: (RoutineItemModel, Boolean) -> Unit
    ): AdapterDelegate<List<RoutineBaseItemModel>> {
        return adapterDelegateViewBinding<RoutineItemModel, RoutineBaseItemModel, ItemRoutineBinding>(
            { layoutInflater, parent ->
                ItemRoutineBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                with(binding) {
                    tvTitle.text = item.title
                    tvDescription.apply { isVisible = item.description?.also { text = it } != null }
                    tvStart.apply {
                        isVisible = item.startTime?.also {
                            text = context.getString(R.string.start_at, it)
                        } != null
                    }
                    tvCompletionTime.apply {
                        isVisible = item.timeToComplete?.also { text = "$it on Completion" } != null
                    }
                    binding.routineHolder.setOnLongClickListener {
                        if (!cbDone.isChecked) {
                            onClickListener.invoke(item)
                        }
                        true
                    }

                    cbDone.setOnCheckedChangeListener(null)
                    cbDone.isChecked = item.isDone

                    // Apply initial "Done" state
                    binding.routineHolder.alpha = if (item.isDone) 0.3f else 1f

                    cbDone.setOnCheckedChangeListener { _, isChecked ->
                        onDoneChanged(item, isChecked)
                        // Update UI immediately
                        binding.routineHolder.alpha = if (isChecked) 0.3f else 1f
                        tvTimeLeft.isVisible = !isChecked && item.startTime != null
                        if (isChecked) {
                            (binding.root.tag as? Runnable)?.let { binding.root.removeCallbacks(it) }
                        }
                    }

                    // Live countdown update
                    (binding.root.tag as? Runnable)?.let { binding.root.removeCallbacks(it) }
                    if (item.startTime != null && !item.isDone) {
                        tvTimeLeft.isVisible = true
                        val timerRunnable = object : Runnable {
                            override fun run() {
                                tvTimeLeft.text = getTimeLeft(
                                    item.startTime!!,
                                    binding.tvTimeLeft
                                )
                                binding.root.postDelayed(this, 1000)
                            }
                        }
                        binding.root.tag = timerRunnable
                        binding.root.post(timerRunnable)
                    } else {
                        tvTimeLeft.isVisible = false
                    }
                }
            }
            onViewDetachedFromWindow {
                (binding.root.tag as? Runnable)?.let { binding.root.removeCallbacks(it) }
            }
        }
    }

    private fun getTimeLeft(
        startTime: String,
        textView: TextView
    ): String {
        val now = Calendar.getInstance()
        val startCal = Calendar.getInstance().apply {
            val parts = startTime.split(":")
            if (parts.size == 2) {
                set(Calendar.HOUR_OF_DAY, parts[0].toInt())
                set(Calendar.MINUTE, parts[1].toInt())
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        }

        return when {
            now.before(startCal) -> {
                val diff = startCal.timeInMillis - now.timeInMillis
                textView.setTextColor(
                    ContextCompat.getColor(
                        textView.context,
                        R.color.text_secondary
                    )
                )
                "Time left: ${formatMillis(diff)}"
            }

            else -> {
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.er_red))
                "Need to be done"
            }
        }
    }

    private fun formatMillis(millis: Long): String {
        val totalSeconds = millis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format(Locale.ROOT, "%02d:%02d:%02d", hours, minutes, seconds)
    }
}
