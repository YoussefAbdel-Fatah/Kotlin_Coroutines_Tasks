package com.example.architechturestartercode.presentation.allmovies.presenter

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architechturestartercode.data.movie.MoviesRepository
import com.example.architechturestartercode.data.movie.model.Movie
import kotlinx.coroutines.launch

class AllMoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _allMovies = MutableLiveData<List<Movie>>()
    val allMovies: LiveData<List<Movie>>
        get() = _allMovies
    private val _addSuccess = MutableLiveData<Boolean>()
    val addSuccess: LiveData<Boolean>
        get() = _addSuccess
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error
    private val moviesRepository = MoviesRepository(application)

    init {
        getAllMovies()
    }

    fun getAllMovies() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val movies = moviesRepository.getAllMovies()
                _isLoading.value = false
                _allMovies.value = movies
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = e.message
            }
        }
    }

    fun addToFav(movie: Movie) {
        viewModelScope.launch {
            moviesRepository.insertMovieToFav(movie)
            _addSuccess.value = true
        }
    }

}

