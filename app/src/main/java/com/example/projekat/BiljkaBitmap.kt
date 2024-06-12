package com.example.projekat

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "BiljkaBitmap",
    foreignKeys = [ForeignKey(entity = Biljka::class,
        parentColumns = ["id"],
        childColumns = ["idBiljke"],
        onDelete = ForeignKey.CASCADE)]) //ovo on delete znaci ako se biljka obrise da se odma i ova orbise vezana za nju
data class BiljkaBitmap(
    @PrimaryKey (autoGenerate = true) val id: Long?=null,
    @ColumnInfo(name="idBiljke") val idBiljke: Long,
    @TypeConverters(BitmapConverter::class) val bitmap: Bitmap
    )
