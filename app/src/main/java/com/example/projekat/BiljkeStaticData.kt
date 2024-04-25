package com.example.projekat

val biljkestatic = listOf(
    Biljka(
        naziv = "Bosiljak (Ocimum basilicum)",
        porodica = "Lamiaceae (usnate)",
        medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
        jela = listOf("Salata od paradajza", "Punjene tikvice"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
    ),
    Biljka(
        naziv = "Nana (Mentha spicata)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO,
            MedicinskaKorist.PROTIVBOLOVA),
        profilOkusa = ProfilOkusaBiljke.MENTA,
        jela = listOf("Jogurt sa voćem", "Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljiste.GLINENO, Zemljiste.CRNICA)
    ),
    Biljka(
        naziv = "Kamilica (Matricaria chamomilla)",
        porodica = "Asteraceae (glavočike)",
        medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE,
            MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Čaj od kamilice"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Ružmarin (Rosmarinus officinalis)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO,
            MedicinskaKorist.REGULACIJAPRITISKA),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Pečeno pile", "Grah","Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.SLJUNKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Lavanda (Lavandula angustifolia)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE,
            MedicinskaKorist.PODRSKAIMUNITETU),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Jogurt sa voćem"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(naziv="Aloe vera (Aloe Barbadensis)",
        porodica="Asphodelaceae (čepljezovke)",
        medicinskoUpozorenje="Aloe vera može izazvati alergijske reakcije na koži kod nekih ljudi. Unutarnja upotreba može " +
                "uzrokovati probavne smetnje ili interakcije s lijekovima. Trudnice, dojilje i osobe s kroničnim bolestima trebaju se posavjetovati s liječnikom prije upotrebe.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO,MedicinskaKorist.SMIRENJE),
        profilOkusa=ProfilOkusaBiljke.GORKO,
        jela=listOf("Morska hrana","Smoothie","Salata"),
        klimatskiTipovi = listOf(KlimatskiTip.SUHA,KlimatskiTip.TROPSKA),
        zemljisniTipovi=listOf(Zemljiste.PJESKOVITO,Zemljiste.KRECNJACKO)),

    Biljka(naziv="Đumbir (Zingiber officinale)",porodica="Zingiberaceae (liliopside)",
        medicinskoUpozorenje="Đumbir može interagirati s određenim lijekovima, pa je važno konzultirati se s liječnikom prije konzumacije, posebno ako uzimate lijekove za razrjeđivanje krvi ili regulaciju šećera u krvi. " +
                "Trudnice i dojilje trebaju izbjegavati veće količine đumbira i konzultirati se s liječnikom prije konzumacije.",
        medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa=ProfilOkusaBiljke.LJUTO,
        jela=listOf("Čaj","Marinirana piletina s đumbirom","Pečena riba","Kolači"),
        klimatskiTipovi = listOf(KlimatskiTip.SUHA,KlimatskiTip.SREDOZEMNA),
        zemljisniTipovi = listOf(Zemljiste.GLINENO)),

    Biljka(naziv="Hibiskus (Rosa Sinensis)",porodica="Malvaceae (sljez)",
        medicinskoUpozorenje="Hibiskus može djelovati kao diuretik (povećava mokrenje) i smanjiti krvni tlak. Stoga, osobe koje uzimaju lijekove za regulaciju krvnog tlaka ili diuretike trebale bi se posavjetovati sa svojim liječnikom prije konzumiranja hibiskusa kako bi izbjegli moguće interakcije lijekova.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO),
        profilOkusa=ProfilOkusaBiljke.SLATKI,
        jela=listOf("Salata","Džem od hibiskusa","Hibiskusni umak"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA,KlimatskiTip.SREDOZEMNA),
        zemljisniTipovi = listOf(Zemljiste.CRNICA)),
    Biljka(naziv="Limun (Citrus Limon)", porodica="Rutaceae (citrus)",
        medicinskoUpozorenje="Limunov sok može izazvati iritaciju kože kod nekih ljudi, posebno ako se koristi neposredno prije izlaganja suncu." +
                "Konzumacija velikih količina limunovog soka može izazvati iritaciju sluznice želuca ili crijeva kod nekih ljudi, posebno onih s osjetljivim želucem ili problemima s kiselinom. " +
                " Limun može interagirati s određenim lijekovima, posebno onima koji su osjetljivi na kiselost želuca.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO,MedicinskaKorist.PODRSKAIMUNITETU),
        profilOkusa=ProfilOkusaBiljke.CITRUSNI,
        jela=listOf("Pasta sa limunom","Lemoncake","Lignje s limunom"),
        klimatskiTipovi=listOf(KlimatskiTip.TROPSKA,KlimatskiTip.SUHA),
        zemljisniTipovi=listOf(Zemljiste.CRNICA)),
    Biljka(naziv="Bijeli luk (Allium sativum)", porodica="Aliaceae (lukovke)",
        medicinskoUpozorenje="Za neke ljude, konzumacija luka može izazvati probavne smetnje poput nadutosti, plinova ili žgaravice." +
                "Prijeći rukama preko luka ili rezanje luka može izazvati iritaciju kože ili suze zbog sumpornih spojeva koji se oslobađaju tijekom obrade luka. Preporučuje se nošenje rukavica prilikom rezanja luka kako bi se smanjila iritacija kože.",
        medicinskeKoristi=listOf(MedicinskaKorist.PODRSKAIMUNITETU,MedicinskaKorist.REGULACIJAPRITISKA),
        profilOkusa=ProfilOkusaBiljke.LJUTO,
        jela=listOf("Garlic bread","Tjestenina s lukom i sirom","Pire od krompira s lukom"),
        klimatskiTipovi = listOf(KlimatskiTip.PLANINSKA,KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljiste.KRECNJACKO))
)
val noveBiljkeDodane=mutableListOf<Biljka>()

fun getBiljke(): List<Biljka>{
    return biljkestatic+ noveBiljkeDodane
}

fun dodajNoveBiljke(biljka : Biljka)
{
    noveBiljkeDodane.add(biljka)
}

fun getListaOkusa(): MutableList<String>{
    return mutableListOf(
        ProfilOkusaBiljke.GORKO.opis,
        ProfilOkusaBiljke.SLATKI.opis,
        ProfilOkusaBiljke.CITRUSNI.opis,
        ProfilOkusaBiljke.MENTA.opis,
        ProfilOkusaBiljke.BEZUKUSNO.opis,
        ProfilOkusaBiljke.LJUTO.opis,
        ProfilOkusaBiljke.AROMATICNO.opis,
        ProfilOkusaBiljke.KORIJENASTO.opis
    )
}

fun getMedicinskeKoristi(): MutableList<String>{
    return mutableListOf(
        MedicinskaKorist.SMIRENJE.opis,
        MedicinskaKorist.PROTUUPALNO.opis,
        MedicinskaKorist.PROTIVBOLOVA.opis,
        MedicinskaKorist.PODRSKAIMUNITETU.opis,
        MedicinskaKorist.REGULACIJAPROBAVE.opis,
        MedicinskaKorist.REGULACIJAPRITISKA.opis
    )
}

fun getKlimatskeTipove(): MutableList<String>{
    return mutableListOf(
        KlimatskiTip.UMJERENA.opis, KlimatskiTip.SUHA.opis,
        KlimatskiTip.TROPSKA.opis,KlimatskiTip.SREDOZEMNA.opis,
        KlimatskiTip.SUBTROPSKA.opis, KlimatskiTip.PLANINSKA.opis
    )
}

fun getZemljisneTipove() :MutableList<String>{
    return mutableListOf(
        Zemljiste.KRECNJACKO.naziv, Zemljiste.CRNICA.naziv,
        Zemljiste.GLINENO.naziv, Zemljiste.ILOVACA.naziv,
        Zemljiste.PJESKOVITO.naziv, Zemljiste.SLJUNKOVITO.naziv
    )
}