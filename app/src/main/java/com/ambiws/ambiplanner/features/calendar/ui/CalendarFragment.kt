package com.ambiws.ambiplanner.features.calendar.ui

import android.view.View
import com.ambiws.ambiplanner.base.BaseFragment
import com.ambiws.ambiplanner.databinding.CalendarDayBinding
import com.ambiws.ambiplanner.databinding.CalendarMonthHeaderBinding
import com.ambiws.ambiplanner.databinding.FragmentCalendarBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale
import com.ambiws.ambiplanner.R
import com.ambiws.ambiplanner.features.home.domain.model.DailySuccess
import com.ambiws.ambiplanner.utils.extensions.subscribe

class CalendarFragment : BaseFragment<CalendarViewModel, FragmentCalendarBinding>(
    FragmentCalendarBinding::inflate
) {

    override fun setupObservers() {
        super.setupObservers()
        subscribe(viewModel.successStats) { stats ->
            with(binding) {
                tvCompleted.text = getString(R.string.finished_days, stats[DailySuccess.ALL_DONE] ?: 0)
                tvMixed.text = getString(R.string.mixed_days, stats[DailySuccess.SOME_DONE] ?: 0)
                tvSkipped.text = getString(R.string.skipped_days, stats[DailySuccess.NONE_DONE] ?: 0)
            }
        }
    }

    override fun setupUi() {
        super.setupUi()

        val monthsToAdd = 100L
        val today = LocalDate.now()
        with(binding) {
            calendar.dayBinder = object : MonthDayBinder<DayViewContainer> {
                override fun create(view: View) = DayViewContainer(view)
                override fun bind(container: DayViewContainer, data: CalendarDay) {
                    container.textView.text = data.date.dayOfMonth.toString()
                    
                    if (data.position == DayPosition.MonthDate && data.date.isBefore(today)) {
                        viewModel.getDailySuccessForDate(data.date)?.let { success ->
                            val backgroundRes = when (success) {
                                DailySuccess.ALL_DONE -> R.color.ltgreen
                                DailySuccess.SOME_DONE -> R.color.ltyellow
                                DailySuccess.NONE_DONE -> R.color.ltred
                            }
                            container.textView.setBackgroundResource(backgroundRes)
                        } ?: run {
                            container.textView.background = null
                        }
                    } else {
                        container.textView.background = null
                    }
                }
            }

            calendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthHeaderContainer> {
                override fun create(view: View) = MonthHeaderContainer(view)
                override fun bind(container: MonthHeaderContainer, data: CalendarMonth) {
                    val date = data.yearMonth
                    val info = "${date.month.name.lowercase().capitalize(Locale.getDefault())}, ${date.year}"
                    container.textView.text = info
                }
            }

            val currentMonth = YearMonth.now()
            val startMonth = currentMonth.minusMonths(monthsToAdd)
            val endMonth = currentMonth.plusMonths(monthsToAdd)
            val firstDayOfWeek = firstDayOfWeekFromLocale()
            calendar.setup(startMonth, endMonth, firstDayOfWeek)
            calendar.scrollToMonth(currentMonth)
        }
    }
}

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = CalendarDayBinding.bind(view).calendarDayText
}

class MonthHeaderContainer(view: View) : ViewContainer(view) {
    val textView = CalendarMonthHeaderBinding.bind(view).calendarMonthHeaderText
}
