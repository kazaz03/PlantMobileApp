package com.example.projekat

data class Biljka(
    var naziv:String,
    var porodica: String,
    var medicinskoUpozorenje: String,
    val medicinskeKoristi: List<MedicinskaKorist>,
    val profilOkusa: ProfilOkusaBiljke?,
    var jela: List<String>,
    var klimatskiTipovi: List<KlimatskiTip>,
    var zemljisniTipovi: List<Zemljiste>,
    var slika: String
)//: Serializable
