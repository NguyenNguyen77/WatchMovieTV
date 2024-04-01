package com.nguyennk.movieapp.repository

import com.nguyennk.movieapp.model.DetailMovie
import com.nguyennk.movieapp.model.PhimLeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceService {

    @GET("v1/api/danh-sach/phim-le")
    suspend fun getPhimLe(): PhimLeResponse

    @GET("v1/api/danh-sach/phim-bo")
    suspend fun getPhimBo(): PhimLeResponse

    @GET("phim/{slug}")
    suspend fun playMovie(@Path("slug")slug: String): DetailMovie

}