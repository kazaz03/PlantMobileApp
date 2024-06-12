package com.example.projekat

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    companion object {
        lateinit var db: SupportSQLiteDatabase
        lateinit var context: Context
        lateinit var roomDb: BiljkaDatabase
        lateinit var biljkaDAO: BiljkaDAO

        @BeforeClass
        @JvmStatic
        fun createDB() = runBlocking {
            val scenarioRule = ActivityScenario.launch(MainActivity::class.java)
            context = ApplicationProvider.getApplicationContext()
            roomDb = Room.inMemoryDatabaseBuilder(context, BiljkaDatabase::class.java).build()
            biljkaDAO = roomDb.biljkaDAO()
            biljkaDAO.getAllBiljkas()
            db = roomDb.openHelper.readableDatabase

        }
    }

    @Test
    fun testDodajBiljku() = runBlocking {
        val biljka = Biljka(
            id = 1,
            naziv = "Test Biljka",
            porodica = "Test Porodica",
            medicinskoUpozorenje = "Test Upozorenje",
            jela = listOf("Test Jelo"),
            klimatskiTipovi = listOf(KlimatskiTip.TROPSKA),
            zemljisniTipovi = listOf(Zemljiste.KRECNJACKO,Zemljiste.CRNICA),
            medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPRITISKA),
            profilOkusa = ProfilOkusaBiljke.KORIJENASTO
        )
        biljkaDAO.saveBiljka(biljka)
        val biljke = biljkaDAO.getAllBiljkas()
        assertThat(biljke[biljke.size-1], `is`(equalTo(biljka)))
    }

    @Test
    fun testObrisiSve() = runBlocking {
        val biljka = Biljka(
            id = 1,
            naziv = "Test Biljka",
            porodica= "Test Porodica",
            medicinskoUpozorenje = "Test Upozorenje",
            jela = listOf("Test Jelo"),
            klimatskiTipovi = listOf(KlimatskiTip.TROPSKA,KlimatskiTip.SREDOZEMNA),
            zemljisniTipovi = listOf(Zemljiste.KRECNJACKO),
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO),
            profilOkusa = ProfilOkusaBiljke.MENTA
        )
        biljkaDAO.saveBiljka(biljka)
        biljkaDAO.clearData()
        val biljke = biljkaDAO.getAllBiljkas()
        assertThat(biljke.size, `is`(equalTo(0)))
    }
}