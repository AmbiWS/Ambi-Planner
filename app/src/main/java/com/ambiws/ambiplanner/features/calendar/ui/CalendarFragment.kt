package com.ambiws.ambiplanner.features.calendar.ui

import android.view.View
import com.ambiws.ambiplanner.base.BaseFragment
import com.ambiws.ambiplanner.databinding.CalendarDayBinding
import com.ambiws.ambiplanner.databinding.CalendarMonthHeaderBinding
import com.ambiws.ambiplanner.databinding.FragmentCalendarBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.YearMonth

class CalendarFragment : BaseFragment<CalendarViewModel, FragmentCalendarBinding>(
    FragmentCalendarBinding::inflate
) {

    override fun setupUi() {
        super.setupUi()

        val monthsToAdd = 100L
        with(binding) {
            calendar.dayBinder = object : MonthDayBinder<DayViewContainer> {
                override fun create(view: View) = DayViewContainer(view)
                override fun bind(container: DayViewContainer, data: CalendarDay) {
                    container.textView.text = data.date.dayOfMonth.toString()
                }
            }

            calendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthHeaderContainer> {
                override fun create(view: View) = MonthHeaderContainer(view)
                override fun bind(container: MonthHeaderContainer, data: CalendarMonth) {
                    container.textView.text = data.yearMonth.month.name
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
