package com.tinkofffilm.presentation.populare

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tinkofffilm.R
import com.tinkofffilm.data.MoviesRepositoryImpl
import com.tinkofffilm.data.pojo.Movie
import com.tinkofffilm.data.pojo.MovieRepo
import com.tinkofffilm.data.pojo.ResponseServer
import com.tinkofffilm.domain.DeleteMovieIFromDBUseCase
import com.tinkofffilm.domain.InsertMovieInDBUseCase
import com.tinkofffilm.domain.LoadAllMoviesFromDBUseCase
import com.tinkofffilm.domain.LoadPopularMoviesUseCase
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
class PopularViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val repository = MoviesRepositoryImpl(application)
    private val insertMovie = InsertMovieInDBUseCase(repository)
    private val loadPopularMoviesFromApi = LoadPopularMoviesUseCase(repository)
    private val loadAllMoviesFromDB = LoadAllMoviesFromDBUseCase(repository)
    private val removeMovie = DeleteMovieIFromDBUseCase(repository)

    private var currentPage = 1

    private val allMovies = MutableLiveData<ResponseServer>()
    val allMoviesLD: LiveData<ResponseServer>
        get() = allMovies

    private val progressBar = MutableLiveData(true)
    val progressBarLD: LiveData<Boolean>
        get() = progressBar

    private val noConnect = MutableLiveData(false)
    val noConnectLD: LiveData<Boolean>
        get() = noConnect

    private val isEmpty = MutableLiveData<Boolean>()
    val isEmptyLD: LiveData<Boolean>
        get() = isEmpty

    private val isLoad = MutableLiveData(false)

    private val listFavorite = MutableLiveData<MutableList<MovieRepo?>>()
    val isFavoriteLD: LiveData<MutableList<MovieRepo?>>
        get() = listFavorite

    init {
        loadMovies(currentPage)
        loadFavoriteMovie()
    }

    private fun loadFavoriteMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            listFavorite.postValue(loadAllMoviesFromDB.loadAllMoviesFromDB())
        }
    }

    fun insertInDB(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {

            Log.i("MyLog","Insert 2.1 - $movie + ${movie.favorite} ")
           // if (movie.favorite != 1) {
                val newMovie = MovieRepo()
                Log.i("MyLog","Insert 2 - $movie")
                insertNewMovie(newMovie, movie)
           // }
        }
    }

    fun removeMovieVM(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            removeMovie.deleteMovieInDB(id)
        }
    }

    private fun insertNewMovie(
        newMovie: MovieRepo,
        movie: Movie
    ) {
        newMovie.nameRu = movie.nameRu
        newMovie.favorite = 1
        newMovie.genres = movie.genres[0].toString()
        newMovie.year = movie.year
        newMovie.countries = movie.countries[0].toString()
        newMovie.kinopoiskId = movie.kinopoiskId
        newMovie.posterUrl = movie.posterUrl
        val temp = movie.ratingKinopoisk
        if (temp == null) {
            newMovie.ratingKinopoisk = "0.0"
        } else {
            newMovie.ratingKinopoisk = temp
        }
        Log.i("MyLog","Insert 3 - $newMovie")
        insertMovie.insertMovieInDB(newMovie)
    }

    fun loadMovies(page: Int) {
        val disposable = loadPopularMoviesFromApi.loadPopularMovies(page)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSubscribe {
                progressBar
                isLoad.value = false

            }
            ?.doAfterTerminate {
                progressBar.value = false
                isLoad.value = true
            }
            ?.subscribe({
                noConnect.value = false

                isEmpty.value = it.items.isEmpty()

                allMovies.value = it
            }, {
                noConnect.value = true
                loadMovies(page)
            })

        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }

    fun checkForFavorite(listFromAPI: List<Movie>, listFromDB: List<MovieRepo?>) {
        viewModelScope.launch(Dispatchers.IO) {
            for (movieApi: Movie in listFromAPI.listIterator())
                for (movieDb: MovieRepo? in listFromDB.listIterator()) {
                    if (movieApi.kinopoiskId == movieDb?.kinopoiskId) {
                        movieApi.favorite = 1
                    }
                }
        }
    }
}