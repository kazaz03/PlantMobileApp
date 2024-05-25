package com.example.projekat

import com.google.gson.annotations.SerializedName

data class SpeciesMainCharacteristics(
    @SerializedName("toxicity") val toxicity: String?
)
