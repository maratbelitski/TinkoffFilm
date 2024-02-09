package com.tinkofffilm.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.tinkofffilm.R
import com.tinkofffilm.data.ApiFactory
import com.tinkofffilm.presentation.adapters.MovieAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        val myAdapter = MovieAdapter()
        recyclerView.adapter = myAdapter


            val disposable = ApiFactory.apiService.loadMovies()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({

                Log.i("MyLog", it.items.toString())
                    myAdapter.submitList(it.items)
                }, {
                     })


        }
}