package com.example.projekat

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    @TypeConverter
    fun fromStoredString(value: String?): List<String> {
        return value?.split("\\s*,\\s*".toRegex())?.dropLastWhile { it.isEmpty() } ?: emptyList()
    }

    @TypeConverter
    fun toStoredString(list: List<String>?): String {
        return list?.joinToString(",") ?: ""
    }
}