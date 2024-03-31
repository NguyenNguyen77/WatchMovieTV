package com.nguyennk.movieapp.repository

import com.nguyennk.movieapp.model.DetailMovie
import com.nguyennk.movieapp.model.PhimLeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceService {

    @GET("v1/api/danh-sach/phim-le")
    suspend fun getPhimLe(): PhimLeResponse // Assuming TVResponse is a data class representing the response

    @GET("phim/{slug}")
    suspend fun playMovie(@Path("slug")slug: String): DetailMovie // Assuming TVResponse is a data class representing the response

}