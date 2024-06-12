package com.example.projekat

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities= arrayOf(Biljka::class,BiljkaBitmap::class),version=2)
@TypeConverters(MedicinskaKoristConverter::class, ProfilOkusaConverter::class,BitmapConverter::class,
    KlimatskiTipConverter::class,StringListConverter::class,ZemljisteTipConverter::class)
abstract class BiljkaDatabase : RoomDatabase(){
    abstract fun biljkaDAO(): BiljkaDAO
    companion object{
        private var INSTANCE: BiljkaDatabase?=null
        fun getInstance(context: Context): BiljkaDatabase {
            if (INSTANCE == null) {
                synchronized(BiljkaDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        //ova funkcija ispod je za kreiranje instance baze
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BiljkaDatabase::class.java,
                "biljke-db"
            ).build()//fallbackToDestructiveMigration().build()
    }
}