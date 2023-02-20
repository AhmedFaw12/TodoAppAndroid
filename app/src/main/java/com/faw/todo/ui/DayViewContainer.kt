package com.faw.todo.ui

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.faw.todo.R
import com.faw.todo.databinding.CalendarDayLayoutBinding
import com.kizitonwose.calendar.view.ViewContainer


class DayViewContainer(view:View): ViewContainer(view) {
    val dayTextView:TextView = view.findViewById(R.id.calendarDayText)
    val dayOfWeekTextView:TextView = view.findViewById(R.id.calenderDayOfWeekText)
}


