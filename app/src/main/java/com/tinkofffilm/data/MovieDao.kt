package com.tinkofffilm.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tinkofffilm.data.pojo.MovieRepo

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieTable")
    fun getAllMovies(): MutableList<MovieRepo>
    @Query("DELETE FROM MovieTable WHERE kinopoiskId = :kinopoiskId")
    fun removeMovie(kinopoiskId:Int)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie:MovieRepo)
}