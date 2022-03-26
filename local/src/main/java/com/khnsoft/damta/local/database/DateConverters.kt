package com.khnsoft.damta.local.database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateConverters {

    @TypeConverter
    fun toTimestamp(date: LocalDate?): Long? = date?.toEpochDay()

    @TypeConverter
    fun toTimestamp(dateTime: LocalDateTime?): Long? = dateTime?.toEpochSecond(ZoneOffset.UTC)

    @TypeConverter
    fun toDate(timestamp: Long?): LocalDate? = timestamp?.let {
        LocalDate.ofEpochDay(timestamp)
    }

    @TypeConverter
    fun toDateTime(timestamp: Long?): LocalDateTime? = timestamp?.let {
        LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC)
    }
}