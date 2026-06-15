package com.ambiws.ambiplanner.features.calendar.ui

import android.view.View
import com.ambiws.ambiplanner.base.BaseFragment
import com.ambiws.ambiplanner.databinding.CalendarDayBinding
import com.ambiws.ambiplanner.databinding.FragmentCalendarBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.YearMonth

class CalendarFragment : BaseFragment<CalendarViewModel, FragmentCalendarBinding>(
    FragmentCalendarBinding::inflate
) {

    override fun setupUi() {
        super.setupUi()

        val monthsToAdd = 100L
        binding.calendar.dayBinder = object : MonthDayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
            }
        }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(monthsToAdd)
        val endMonth = currentMonth.plusMonths(monthsToAdd)
        val firstDayOfWeek = firstDayOfWeekFromLocale()
        binding.calendar.setup(startMonth, endMonth, firstDayOfWeek)
        binding.calendar.scrollToMonth(currentMonth)
    }
}

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = CalendarDayBinding.bind(view).calendarDayText
}
