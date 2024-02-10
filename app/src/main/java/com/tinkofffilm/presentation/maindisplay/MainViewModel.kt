package com.tinkofffilm.presentation.maindisplay

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinkofffilm.data.MoviesRepositoryImpl
import com.tinkofffilm.data.pojo.Movie
import com.tinkofffilm.data.pojo.ResponseServer
import com.tinkofffilm.data.retrofit.ApiService
import com.tinkofffilm.domain.LoadAllMoviesUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author Belitski Marat
 * @date  10.02.2024
 * @project TinkoffFilm
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val repository = MoviesRepositoryImpl(application)

    private val loadAllMoviesFromApi = LoadAllMoviesUseCase(repository)

    private val allMovies = MutableLiveData<ResponseServer>()

    val allMoviesLD: LiveData<ResponseServer>
        get() = allMovies

    private val progressBar = MutableLiveData(true)
    val progressBarLD: LiveData<Boolean>
        get() = progressBar

    private val noConnect = MutableLiveData(false)
    val noConnectLD: LiveData<Boolean>
        get() = noConnect

    init {
        loadMovies()
    }

     fun loadMovies() {
        val disposable = loadAllMoviesFromApi.loadAllMovies()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSubscribe {
                progressBar
            }
            ?.doAfterTerminate {
                progressBar.value = false
            }
            ?.subscribe({
                noConnect.value = false
                allMovies.value = it

            }, {
                noConnect.value = true
                loadMovies()
            })

        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }
}