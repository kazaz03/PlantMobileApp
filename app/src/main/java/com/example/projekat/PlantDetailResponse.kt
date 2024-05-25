package com.example.projekat

import com.google.gson.annotations.SerializedName

data class PlantDetailResponse(
    @SerializedName("data") val data: PlantDetails
)
