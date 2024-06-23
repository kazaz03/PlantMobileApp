package com.example.projekat

import android.graphics.Bitmap
import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Dao
interface BiljkaDAO {

    @Insert
    suspend fun insertBiljka(biljka: Biljka)
    @Transaction
    suspend fun saveBiljka(biljka: Biljka): Boolean = withContext(Dispatchers.IO)
    {
        try{
        val sveBiljke=getAllBiljkas()
        for(biljke in sveBiljke)
        {
            val naziv1= izvuciLatinski(biljke.naziv)
            val naziv2= izvuciLatinski(biljka.naziv)
            if(naziv1.equals(naziv2))
                return@withContext false
        }
            insertBiljka(biljka)
            return@withContext false
        }catch(e: Exception){
            return@withContext false
        }
    }

    @Query("SELECT * FROM Biljka WHERE naziv = :naziv")
    suspend fun getBiljkaByName(naziv: String): Biljka?
    @Transaction
    suspend fun addImage(idBiljke: Long?, bitmap: Bitmap): Boolean = withContext(Dispatchers.IO){
        try{
            if(idBiljke!=null){
            val biljka=getBiljkaById(idBiljke)
            if (biljka == null) {
                Log.d("BiljkaDAO", "Biljka with id $idBiljke not found")
                return@withContext false
            }

            val slikaBiljke=getImageByBiljkaId(idBiljke)
            if (slikaBiljke != null) {
                Log.d("BiljkaDAO", "Image for biljka with id $idBiljke already exists")
                return@withContext false
            }

            val biljkabitmap=BiljkaBitmap(null,idBiljke,bitmap)
            addBiljkaBitmap(biljkabitmap)
            return@withContext  true
            }else return@withContext false
        }catch(e: Exception){
            false
        }
    }

    suspend fun fixOfflineBiljka(): Int = withContext(Dispatchers.IO){
        val offlineBiljkas = getOfflinePlants()
        var updatedCount = 0
        for(plant in offlineBiljkas){
            val original=plant
            val novabiljka=TrefleDAO().fixData(plant)
            if(original!=novabiljka){
                updateBiljka(novabiljka)
                updatedCount++
            }
        }
        return@withContext updatedCount
    }

    @Insert
    suspend fun addBiljkaBitmap(biljkaBitmap:BiljkaBitmap)

    @Query("SELECT * FROM Biljka")
    suspend fun getAllBiljkas(): List<Biljka>

    @Query("DELETE FROM Biljka")
    suspend fun clearData()
    //dodat metodu za update ostalih podataka biljke

    @Update
    suspend fun updateBiljka(biljka: Biljka): Int

    @Query("SELECT * FROM Biljka WHERE onlineChecked = false")
    suspend fun getOfflinePlants(): List<Biljka>

    @Query("SELECT * FROM Biljka WHERE id=:id")
    suspend fun getBiljkaById(id: Long): Biljka?

    @Query("SELECT * FROM BiljkaBitmap WHERE idBiljke=:idBiljke")
    suspend fun getImageByBiljkaId(idBiljke: Long): BiljkaBitmap?

}

fun izvuciLatinski(puniNaziv: String):String{
    val pattern = "\\((.*?)\\)".toRegex()
    val matchResult = pattern.find(puniNaziv)
    return matchResult?.groupValues?.get(1)?:""
}