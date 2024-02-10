package com.tinkofffilm.presentation.maindisplay

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinkofffilm.data.MoviesRepositoryImpl
import com.tinkofffilm.data.pojo.ResponseServer
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

    private var currentPage = 1;

    companion object {

    }

    private val allMovies = MutableLiveData<ResponseServer?>()
    val allMoviesLD: LiveData<ResponseServer?>
        get() = allMovies

    private val progressBar = MutableLiveData(true)
    val progressBarLD: LiveData<Boolean>
        get() = progressBar

    private val noConnect = MutableLiveData(false)
    val noConnectLD: LiveData<Boolean>
        get() = noConnect

    private val isLoad = MutableLiveData(false)
    val isLoadLD: LiveData<Boolean>
        get() = isLoad

    init {
        loadMovies()

    }



    fun loadMovies() {
        val disposable = loadAllMoviesFromApi.loadAllMovies(currentPage)
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
                allMovies.value = it


//                //подгрузка данных
//                var loadedMovies:ResponseServer?  = allMovies.value
//                if (loadedMovies != null) {
//                    loadedMovies = it
//                   allMovies.value = loadedMovies
//                } else {
//                    allMovies.value = it
//                }
//                currentPage++

            }, {
                noConnect.value = true
                loadMovies()
            })

        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }
}