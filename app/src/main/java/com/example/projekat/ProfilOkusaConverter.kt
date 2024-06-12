package com.example.projekat

import androidx.room.TypeConverter

class ProfilOkusaConverter {

    @TypeConverter
    fun fromStoredString(value: String?): ProfilOkusaBiljke? {
        return if (value == null || value.isEmpty()) {
            null
        } else {
            ProfilOkusaBiljke.valueOf(value)
        }
    }
    @TypeConverter
    fun toStoredString(profilOkusa: ProfilOkusaBiljke?): String? {
        return profilOkusa?.name
    }
}
