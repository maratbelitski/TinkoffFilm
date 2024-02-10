package com.tinkofffilm.presentation.detaildisplay

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinkofffilm.data.MoviesRepositoryImpl
import com.tinkofffilm.data.pojo.MovieDetail
import com.tinkofffilm.domain.LoadDetailMoviesUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author Belitski Marat
 * @date  10.02.2024
 * @project TinkoffFilm
 */
class DetailViewModel (application: Application) : AndroidViewModel(application){
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val repository = MoviesRepositoryImpl(application)

    private val loadDetailMoviesFromApi = LoadDetailMoviesUseCase(repository)


    private val detailMovies = MutableLiveData<MovieDetail>()
    val detailMoviesLD: LiveData<MovieDetail>
    get() = detailMovies

    private val progressBar = MutableLiveData(true)
    val progressBarLD: LiveData<Boolean>
        get() = progressBar

    private val noConnect = MutableLiveData(false)
    val noConnectLD: LiveData<Boolean>
        get() = noConnect

    fun loadDetailMovie(idKinopoisk:Int) {
        val disposable = loadDetailMoviesFromApi.loadAllMovies(idKinopoisk)
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
                detailMovies.value = it

            }, {
                noConnect.value = true
                loadDetailMovie(idKinopoisk)
            })

        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }
}