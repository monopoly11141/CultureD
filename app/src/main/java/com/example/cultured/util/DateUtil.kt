package com.example.cultured.util

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object DateUtil {
    val TODAY_DATE = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    fun String.getNDaysAgo(day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -day)
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
    }

}