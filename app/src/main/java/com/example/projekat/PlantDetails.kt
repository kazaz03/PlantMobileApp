package com.example.projekat

import com.google.gson.annotations.SerializedName

data class PlantDetails(
    @SerializedName("id") val id: Int,
    @SerializedName("common_name") val commonName: String?,
    @SerializedName("scientific_name") val scientificName: String,
    @SerializedName("family_common_name") val family_common_name: String?,
    @SerializedName("main_species") val mainSpecies: Species //napravit klasu species
)
