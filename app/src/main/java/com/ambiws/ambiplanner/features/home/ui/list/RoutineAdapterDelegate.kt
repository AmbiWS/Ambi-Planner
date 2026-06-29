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
        onClickListener: (RoutineItemModel) -> Unit
    ): AdapterDelegate<List<RoutineBaseItemModel>> {
        return adapterDelegateViewBinding<RoutineItemModel, RoutineBaseItemModel, ItemRoutineBinding>(
            { layoutInflater, parent ->
                ItemRoutineBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                with (binding) {
                    tvTitle.text = item.title
                    tvDescription.apply { isVisible = item.description?.also { text = it } != null }
                    tvStart.apply { isVisible = item.startTime?.also { text = it } != null }
                    tvCompletionTime.apply { isVisible = item.timeToComplete?.also { text = it } != null }
                    ivEdit.setOnClickListener { onClickListener.invoke(item) }

                    // Live countdown update
                    (root.tag as? Runnable)?.let { root.removeCallbacks(it) }
                    if (item.startTime != null) {
                        tvTimeLeft.isVisible = true
                        val timerRunnable = object : Runnable {
                            override fun run() {
                                tvTimeLeft.text = getTimeLeft(item.startTime!!, item.timeToComplete, binding.tvTimeLeft)
                                root.postDelayed(this, 1000)
                            }
                        }
                        root.tag = timerRunnable
                        root.post(timerRunnable)
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

    private fun getTimeLeft(startTime: String, timeToComplete: String?, textView: TextView): String {
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

        val durationMillis = parseDuration(timeToComplete)
        val endCal = (startCal.clone() as Calendar).apply {
            add(Calendar.MILLISECOND, durationMillis.toInt())
        }

        return when {
            now.before(startCal) -> {
                val diff = startCal.timeInMillis - now.timeInMillis
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.text_secondary))
                "Time left: ${formatMillis(diff)}"
            }
            now.before(endCal) -> {
                val diff = endCal.timeInMillis - now.timeInMillis
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.text_secondary))
                "Time left: ${formatMillis(diff)}"
            }
            else -> {
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.ltred))
                "Need to be done"
            }
        }
    }

    private fun parseDuration(timeToComplete: String?): Long {
        if (timeToComplete == null) return 0
        val regex = "(\\d+)h (\\d+)m".toRegex()
        val matchResult = regex.find(timeToComplete)
        return if (matchResult != null) {
            val (h, m) = matchResult.destructured
            (h.toLong() * 3600 + m.toLong() * 60) * 1000
        } else 0
    }

    private fun formatMillis(millis: Long): String {
        val totalSeconds = millis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format(Locale.ROOT, "%02d:%02d:%02d", hours, minutes, seconds)
    }
}
