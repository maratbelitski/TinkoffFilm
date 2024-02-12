package com.tinkofffilm.presentation.favoritedisplay

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tinkofffilm.data.MoviesRepositoryImpl
import com.tinkofffilm.data.pojo.MovieRepo
import com.tinkofffilm.domain.DeleteMovieIFromDBUseCase
import com.tinkofffilm.domain.LoadAllMoviesFromDBUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Belitski Marat
 * @date  10.02.2024
 * @project TinkoffFilm
 */
class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MoviesRepositoryImpl(application)

    private val loadAllMoviesFromDB = LoadAllMoviesFromDBUseCase(repository)
    private val removeMovie = DeleteMovieIFromDBUseCase(repository)

    private val listFavorite = MutableLiveData<MutableList<MovieRepo>>()
    val isFavoriteLD: LiveData<MutableList<MovieRepo>>
        get() = listFavorite


    init {
        loadFavoriteMovie()
    }


    fun loadFavoriteMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            listFavorite.postValue(loadAllMoviesFromDB.loadAllMoviesFromDB())
        }
    }


    fun removeMovieVM(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            removeMovie.deleteMovieInDB(id)
        }
    }
}