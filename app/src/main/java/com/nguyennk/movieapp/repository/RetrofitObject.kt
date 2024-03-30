package com.nguyennk.movieapp.repository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitObject {

    private val BASE_URL = "https://phimapi.com/"

    val apiService: InterfaceService = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build().create(InterfaceService::class.java)

}