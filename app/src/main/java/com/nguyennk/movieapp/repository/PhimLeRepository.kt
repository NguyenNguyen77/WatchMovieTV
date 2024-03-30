package com.nguyennk.movieapp.repository
import android.util.Log
import com.nguyennk.movieapp.model.DataPhimLe
import com.nguyennk.movieapp.model.Episode


private val apiService = RetrofitObject.apiService
class PhimLeRepository {

    // Function to fetch TV shows in the category Phim Le
    suspend fun getPhimLe(): DataPhimLe {
        return apiService.getPhimLe().data // Assuming TVService has appropriate endpoints and data models
    }

    suspend fun playMovie(slug: String): List<Episode> {
        Log.d("NguyenNK2","playMovie msg "+apiService.playMovie(slug).episodes)
        return apiService.playMovie(slug).episodes // Assuming TVService has appropriate endpoints and data models
    }


//    // Function to fetch TV shows in the category Phim Bo
//    suspend fun getPhimBo(): List<TVShow> {
//        return service.getPhimBo().tvShows // Assuming TVService has appropriate endpoints and data models
//    }
//
//    // Function to fetch TV shows in the category Phim Hoat Hinh
//    suspend fun getPhimHoatHinh(): List<TVShow> {
//        return service.getPhimHoatHinh().tvShows // Assuming TVService has appropriate endpoints and data models
//    }
//
//    // Function to fetch latest TV shows
//    suspend fun getPhimMoi(): List<TVShow> {
//        return service.getPhimMoi().tvShows // Assuming TVService has appropriate endpoints and data models
//    }
}