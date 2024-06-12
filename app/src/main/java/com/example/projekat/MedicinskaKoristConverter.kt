package com.example.projekat

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Arrays

class MedicinskaKoristConverter{
    @TypeConverter
    fun fromStoredString(value: String): List<MedicinskaKorist> {
        val dbValues = Arrays.asList(*value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        val enums: MutableList<MedicinskaKorist> = ArrayList()
        for (s in dbValues) {
            enums.add(MedicinskaKorist.valueOf(s!!))
        }
        return enums
    }

    @TypeConverter
    fun toStoredString(medicinskeKoristi: List<MedicinskaKorist>): String {
        val value = StringBuilder()
        for (medicinskaKorist in medicinskeKoristi) {
            value.append(medicinskaKorist.name).append(",")
        }
        // Uklonite zadnji zarez
        if (value.length > 0) {
            value.setLength(value.length - 1)
        }
        return value.toString()
    }
}
