package com.example.projekat

import android.os.Parcelable
import java.io.Serializable

data class Biljka(
    var naziv:String,
    var porodica: String,
    var medicinskoUpozorenje: String,
    val medicinskeKoristi: List<MedicinskaKorist>,
    val profilOkusa: ProfilOkusaBiljke,
    var jela: MutableList<String>,
    var klimatskiTipovi: MutableList<KlimatskiTip>,
    var zemljisniTipovi: MutableList<Zemljiste>
)//: Serializable
