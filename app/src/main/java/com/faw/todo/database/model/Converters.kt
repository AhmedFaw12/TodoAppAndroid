package com.faw.todo.database.model

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun toDate(milliseconds: Long?): Date? {
        return milliseconds?.let{Date(it)}
    }

    @TypeConverter
    fun DateToTimestamp(date:Date?):Long?{
        return date?.time
    }
}