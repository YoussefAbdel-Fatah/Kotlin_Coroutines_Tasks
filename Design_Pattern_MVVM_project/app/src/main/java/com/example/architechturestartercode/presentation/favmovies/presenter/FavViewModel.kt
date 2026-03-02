package com.example.architechturestartercode.presentation.favmovies.presenter

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.architechturestartercode.data.movie.MoviesRepository
import com.example.architechturestartercode.data.movie.model.Movie
import kotlinx.coroutines.launch

class FavViewModel(val repository: MoviesRepository): ViewModel() {
    val deleteSuccess = MutableLiveData<Boolean>()

    fun getFavMovies(): LiveData<List<Movie>> {

        return repository.getAllFavMovies()
    }

    fun deleteFavMovie(movie: Movie) {
        viewModelScope.launch {
            repository.deleteMovieFromFav(movie)
            deleteSuccess.value = true
        }

    }
}

class FavViewModelFactory(val repository: MoviesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavViewModel(repository) as T
    }
}
