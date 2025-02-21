package com.example.myshows.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.myshows.Movie
import java.util.UUID

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie WHERE status=(:status)")
    fun getMovies(status:Int): LiveData<List<Movie>>
    @Query("SELECT * FROM movie WHERE id=(:id)")
    fun getMovie(id: UUID): LiveData<Movie?>

    @Update
    fun updateMovie(movie: Movie)
}