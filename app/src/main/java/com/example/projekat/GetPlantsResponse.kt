package com.example.projekat

import com.google.gson.annotations.SerializedName

data class GetPlantsResponse(
    @SerializedName("data") val data: List<PlantResponse>
)