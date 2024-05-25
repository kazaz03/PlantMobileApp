package com.example.projekat

import com.google.gson.annotations.SerializedName

data class FlowerOfPlant(
    @SerializedName("color") val color: List<String?>
)
