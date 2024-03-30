package com.nguyennk.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nguyennk.movieapp.model.DataPhimLe
import com.nguyennk.movieapp.repository.PhimLeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class PhimLeViewModelFactory(private val repository:PhimLeRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhimLeViewModel::class.java)) {
            return PhimLeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}