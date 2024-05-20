package com.nguyennk.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nguyennk.movieapp.model.DataPhimLe
import com.nguyennk.movieapp.model.Episode
import com.nguyennk.movieapp.model.Movie
import com.nguyennk.movieapp.repository.PhimLeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class PhimLeViewModel(private val repository:PhimLeRepository) : ViewModel(),
    ViewModelProvider.Factory {

    private val _movieShowLe = MutableLiveData<DataPhimLe>()
    private val _movieShowBo = MutableLiveData<DataPhimLe>()
    private val _playMovie = MutableLiveData<Episode>()
    private val _detailInfoMovie = MutableLiveData<Movie>()
    private val _isPhimBo = MutableLiveData<Boolean>()
    val listPhimLe: LiveData<DataPhimLe> = _movieShowLe
    val listPhimBo: LiveData<DataPhimLe> = _movieShowBo
    val playMovie: LiveData<Episode> = _playMovie
    val detailInfoMovie: LiveData<Movie> = _detailInfoMovie
    val isPhimBo: LiveData<Boolean> = _isPhimBo
    private val repo = PhimLeRepository()
    init {
        loadPhimLe()
        loadPhimBo()
    }

    private fun loadPhimLe() {
        viewModelScope.launch {
            try {
                val phimLe = repo.getPhimLe()
                _movieShowLe.value = phimLe
            } catch (e: Exception) {
                //Log.e(TAG, "Failed to load TV shows", e)
            }
        }
    }

    private fun loadPhimBo() {
        viewModelScope.launch {
            try {
                val phimBo = repo.getPhimBo()
                _movieShowBo.value = phimBo
            } catch (e: Exception) {
                // Handle error appropriately, such as showing an error message
                //Log.e(TAG, "Failed to load TV shows", e)
            }
        }
    }

//    fun playMovie(slug:String) {
//        viewModelScope.launch {
//            try {
//                val playMovie = repo.playMovie(slug).episodes
//                _playMovie.value = playMovie[0]
//            } catch (e: Exception) {
//                // Handle error appropriately, such as showing an error message
//                Log.e("NguyenNK2", "Failed to load TV shows", e)
//            }
//        }
//    }

    fun getInfoMovie(slug:String) {
        viewModelScope.launch {
            try {
                val infoMovie = repo.playMovie(slug)
                _detailInfoMovie.value = infoMovie.movie
                _playMovie.value = infoMovie.episodes[0]
                Log.e("NguyenNK2", "episodes.size ${infoMovie.episodes[0].server_data.size}")
                _isPhimBo.value = infoMovie.episodes[0].server_data.size>1
            } catch (e: Exception) {
                // Handle error appropriately, such as showing an error message
                Log.e("NguyenNK2", "Failed to load TV shows", e)
            }
        }
    }

}