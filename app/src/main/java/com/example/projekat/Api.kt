package com.example.projekat

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("plants/search")
    suspend fun searchPlants(
        @Query("q") query: String,
        @Query("token") apiKey: String ="GY7g1D63OBHFctauOfmzT3gT7nopRKtlrlxf0Ueu1Fk"
    ): Response<GetPlantsResponse>

    //da dobijem sve ostale detalje biljke
    @GET("plants/{id}")
    suspend fun getPlantById(
        @Path("id") id: Int,
        @Query("token") apiKey: String = "GY7g1D63OBHFctauOfmzT3gT7nopRKtlrlxf0Ueu1Fk"
    ): Response<PlantDetailResponse>
}