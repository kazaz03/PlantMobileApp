package com.example.projekat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "Biljka")
data class Biljka(
    @PrimaryKey(autoGenerate = true) val id: Long?=null, //primarni kljuc
    @ColumnInfo(name="naziv") var naziv:String,
    @ColumnInfo (name="porodica") var porodica: String,
    @ColumnInfo(name="medicinskoUpozorenje") var medicinskoUpozorenje: String,
    @TypeConverters(MedicinskaKoristConverter::class)val medicinskeKoristi: List<MedicinskaKorist>,
    @TypeConverters(ProfilOkusaConverter::class) val profilOkusa: ProfilOkusaBiljke?,
    @TypeConverters(StringListConverter::class) var jela: List<String>,
    @TypeConverters(KlimatskiTipConverter::class) var klimatskiTipovi: List<KlimatskiTip>,
    @TypeConverters(ZemljisteTipConverter::class) var zemljisniTipovi: List<Zemljiste>,
    @ColumnInfo(name="onlineChecked") var onlineChecked: Boolean=false
)//: Serializable
