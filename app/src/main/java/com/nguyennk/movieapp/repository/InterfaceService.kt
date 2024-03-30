package com.nguyennk.movieapp.repository

import com.nguyennk.movieapp.model.DetailMovie
import com.nguyennk.movieapp.model.PhimLeResponse
import retrofit2.http.GET

interface InterfaceService {

    @GET("v1/api/danh-sach/phim-le")
    suspend fun getPhimLe(): PhimLeResponse // Assuming TVResponse is a data class representing the response

    @GET("phim/")
    suspend fun playMovie(slug: String): DetailMovie // Assuming TVResponse is a data class representing the response

}