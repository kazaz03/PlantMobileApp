package com.example.projekat

import com.google.gson.annotations.SerializedName

data class GrowthOfFarmingRelatedFields(
    @SerializedName("soil_texture") val soil_texture: Int?,
    @SerializedName("light") val light: Int?,
    @SerializedName("atmospheric_humidity") val atmosphericHumidity: Int?
)
