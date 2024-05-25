package com.example.projekat

import com.google.gson.annotations.SerializedName

data class PlantDetails(
    @SerializedName("id") val id: Int,
    @SerializedName("common_name") val commonName: String?,
    @SerializedName("slug") val slug: String,
    @SerializedName("scientific_name") val scientificName: String,
    @SerializedName("year") val year: Int?,
    @SerializedName("bibliography") val bibliography: String?,
    @SerializedName("author") val author: String?,
    @SerializedName("family_common_name") val family_common_name: String?,
    @SerializedName("genus_id") val genusId: Int,
    @SerializedName("main_species_id") val mainSpeciesId: Int?,
    @SerializedName("vegetable") val vegetable: Boolean?,
    @SerializedName("observations") val observations: String?,
    @SerializedName("main_species") val mainSpecies: Species //napravit klasu species
)
