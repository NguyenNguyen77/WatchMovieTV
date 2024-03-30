package com.nguyennk.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nguyennk.movieapp.model.DataPhimLe
import com.nguyennk.movieapp.model.DetailMovie
import com.nguyennk.movieapp.model.Episode
import com.nguyennk.movieapp.repository.PhimLeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class PhimLeViewModel(private val repository:PhimLeRepository) : ViewModel(),
    ViewModelProvider.Factory {

    private val _movieShow = MutableLiveData<DataPhimLe>()
    private val _playMovie = MutableLiveData<Episode>()
    val listPhimLe: LiveData<DataPhimLe> = _movieShow
    val playMovie: LiveData<Episode> = _playMovie
    private val repo = PhimLeRepository()
    init {
        loadPhimLe()
    }

    private fun loadPhimLe() {
        viewModelScope.launch {
            try {
                val phimLe = repo.getPhimLe()
                _movieShow.value = phimLe
            } catch (e: Exception) {
                // Handle error appropriately, such as showing an error message
                //Log.e(TAG, "Failed to load TV shows", e)
            }
        }
    }

    fun playMovie(slug:String) {
        viewModelScope.launch {
            try {
                val phimLe = repo.playMovie(slug)
                _playMovie.value = phimLe.get(0)
            } catch (e: Exception) {
                // Handle error appropriately, such as showing an error message
                //Log.e(TAG, "Failed to load TV shows", e)
            }
        }
    }

}