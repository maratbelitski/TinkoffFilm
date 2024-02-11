package com.tinkofffilm.presentation.favoritedisplay

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tinkofffilm.data.MoviesRepositoryImpl
import com.tinkofffilm.data.pojo.Movie
import com.tinkofffilm.data.pojo.MovieRepo
import com.tinkofffilm.data.pojo.ResponseServer
import com.tinkofffilm.domain.DeleteMovieIFromDBUseCase
import com.tinkofffilm.domain.InsertMovieInDBUseCase
import com.tinkofffilm.domain.LoadAllMoviesFromDBUseCase
import com.tinkofffilm.domain.LoadAllMoviesUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
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
     fun loadFavoriteMovie(){
        viewModelScope.launch(Dispatchers.IO) {
            listFavorite.postValue(loadAllMoviesFromDB.loadAllMoviesFromDB())
        }
    }

//    fun insertInDB(movie: Movie) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val newMovie = MovieRepo()
//            if (movie.favorite == 0) {
//                insertNewMovie(newMovie, movie)
//            } else {
//                removeMovie.deleteMovieInDB(movie.kinopoiskId)
//            }
//        }
//    }

    fun removeMovieVM(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
           removeMovie.deleteMovieInDB(id)
        }
    }

//    private fun insertNewMovie(
//        newMovie: MovieRepo,
//        movie: Movie
//    ) {
//        newMovie.nameRu = movie.nameRu
//        newMovie.favorite = 1
//        newMovie.genres = movie.genres?.get(0).toString()
//        newMovie.year = movie.year
//        newMovie.countries = movie.countries?.get(0).toString()
//        newMovie.kinopoiskId = movie.kinopoiskId
//        newMovie.posterUrl = movie.posterUrl
//        val temp = movie.ratingKinopoisk
//        if (temp == null) {
//            newMovie.ratingKinopoisk = "0.0"
//        } else {
//            newMovie.ratingKinopoisk = temp
//        }
//
//        insertMovie.insertMovieInDB(newMovie)
//    }


//    fun loadMoviesFromAPI() {
//        val disposable = loadAllMoviesFromApi.loadAllMovies(currentPage)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnSubscribe {
//                progressBar
//                isLoad.value = false
//            }
//            ?.doAfterTerminate {
//                progressBar.value = false
//                isLoad.value = true
//
//            }
//            ?.subscribe({
//                noConnect.value = false
//                allMoviesFromApi.value = it
//            }, {
//                noConnect.value = true
//                loadMoviesFromAPI()
//            })
//
//        if (disposable != null) {
//            compositeDisposable.add(disposable)
//        }
//
//        fun loadFromDB() {
//            allMoviesFromDB.value = loadAllMoviesFromDB.loadAllMoviesFromDB()
//        }

}