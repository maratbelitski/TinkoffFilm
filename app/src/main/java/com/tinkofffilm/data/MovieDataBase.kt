package com.tinkofffilm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.tinkofffilm.data.pojo.MovieRepo

@Database(entities = [MovieRepo::class], version = 1, exportSchema = false)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun getDao(): MovieDao;
    companion object {
        fun getDB(context: Context): MovieDataBase {
            return Room.databaseBuilder(context, MovieDataBase::class.java, "MovieListDB").build()
        }
    }
}