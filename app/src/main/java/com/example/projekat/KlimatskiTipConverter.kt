package com.example.projekat

import androidx.room.TypeConverter
import java.util.Arrays

class KlimatskiTipConverter {
    @TypeConverter
    fun fromStoredString(value: String): List<KlimatskiTip> {
        val dbValues = Arrays.asList(*value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        val enums: MutableList<KlimatskiTip> = ArrayList()
        for (s in dbValues) {
            enums.add(KlimatskiTip.valueOf(s!!))
        }
        return enums
    }

    @TypeConverter
    fun toStoredString(klimatskiTipovi: List<KlimatskiTip>): String {
        val value = StringBuilder()
        for (klimatskiTip in klimatskiTipovi) {
            value.append(klimatskiTip.name).append(",")
        }

        // Uklonite zadnji zarez
        if (value.length > 0) {
            value.setLength(value.length - 1)
        }
        return value.toString()
    }
}
