package com.example.projekat

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {
    @GET("plants/search")
    suspend fun searchPlants(
        @Query("q") query: String,
        @Query("token") apiKey: String =BuildConfig.API_KEY
    ): Response<GetPlantsResponse>

    //da dobijem sve ostale detalje biljke
    @GET("plants/{id}")
    suspend fun getPlantById(
        @Path("id") id: Int,
        @Query("token") apiKey: String = BuildConfig.API_KEY
    ): Response<PlantDetailResponse>

    //flower color poziv
    @GET("plants/search")
    suspend fun getPlantsByFlowerColor(
        @Query("q") query: String,
        @QueryMap filters: Map<String,String>,
        @Query("token") apiKey: String=BuildConfig.API_KEY
    ): Response<GetPlantsResponse>
}