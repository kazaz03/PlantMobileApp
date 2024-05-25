package com.example.projekat

import com.google.gson.annotations.SerializedName

data class Species(
    @SerializedName("family") val family: String,
    @SerializedName("edible") val edible: Boolean?,
    @SerializedName("specifications") val specifications: SpeciesMainCharacteristics,
    @SerializedName("growth") val growth: GrowthOfFarmingRelatedFields,
    @SerializedName("flower") val flower: FlowerOfPlant
)
