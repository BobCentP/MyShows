package com.example.myshows.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.myshows.Movie
import java.util.UUID

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie WHERE status=0")
    fun getMovies(): LiveData<List<Movie>>
    @Query("SELECT * FROM movie WHERE id=(:id)")
    fun getMovie(id: UUID): LiveData<Movie?>

    @Query("SELECT * FROM movie WHERE status=1")
    fun getMoviesWatching(): LiveData<List<Movie>>
    @Query("SELECT * FROM movie WHERE status=2")
    fun getMoviesWillWatching(): LiveData<List<Movie>>
    @Query("SELECT * FROM movie WHERE status=3")
    fun getMoviesViewed(): LiveData<List<Movie>>
    @Query("SELECT * FROM movie WHERE status=4")
    fun getMoviesAbandoned(): LiveData<List<Movie>>

    @Update
    fun updateMovie(movie: Movie)
}