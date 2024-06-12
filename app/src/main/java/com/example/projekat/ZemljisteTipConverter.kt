package com.example.projekat

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Arrays

class ZemljisteTipConverter {
    @TypeConverter
    fun fromStoredString(value: String): List<Zemljiste> {
        val dbValues = Arrays.asList(*value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        val enums: MutableList<Zemljiste> = ArrayList()
        for (s in dbValues) {
            enums.add(Zemljiste.valueOf(s!!))
        }
        return enums
    }

    @TypeConverter
    fun toStoredString(zemljisniTipovi: List<Zemljiste>): String {
        val value = StringBuilder()
        for (zemljisniTip in zemljisniTipovi) {
            value.append(zemljisniTip.name).append(",")
        }
        // Uklonite zadnji zarez
        if (value.length > 0) {
            value.setLength(value.length - 1)
        }
        return value.toString()
    }
}