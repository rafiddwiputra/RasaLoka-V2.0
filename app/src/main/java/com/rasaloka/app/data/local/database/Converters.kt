package com.rasaloka.app.data.local.database

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromLikedByList(value: List<String>): String {

        return value.joinToString(",")
    }

    @TypeConverter
    fun toLikedByList(value: String): List<String> {

        if (value.isEmpty()) {
            return emptyList()
        }

        return value.split(",")
    }
}