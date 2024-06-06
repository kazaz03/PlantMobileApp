package com.example.projekat

import com.google.gson.annotations.SerializedName

data class PlantResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("common_name") val commonName: String,
    @SerializedName("scientific_name") val scientificName: String,
    @SerializedName("family_common_name") val familyCommonName: String,
    @SerializedName("family")  val family: String,
    @SerializedName("image_url")  val imageUrl: String?,
)
