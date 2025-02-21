package com.example.myshows

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.myshows.database.MovieDatabase

import java.util.UUID
import java.util.concurrent.Executors
private const val DATABASE_NAME="database_movie"
class MovieRepository private constructor(context: Context) {


    private val database : MovieDatabase = Room.databaseBuilder(
        context.applicationContext,
        MovieDatabase::class.java,
        DATABASE_NAME
    ).createFromAsset("database/movie.db").build()

    private val movieDao = database.movieDao()
    private val executor = Executors.newSingleThreadExecutor()
    fun getMovies(status:Int): LiveData<List<Movie>> = movieDao.getMovies(status)
    fun getMovie(id: UUID): LiveData<Movie?> = movieDao.getMovie(id)


    fun updateMovie(movie: Movie){
        executor.execute {
            movieDao.updateMovie(movie)
        }
    }

    companion object{
        private var INSTANCE: MovieRepository?=null

        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE= MovieRepository(context)
            }
        }

        fun get(): MovieRepository{
            return INSTANCE?:
            throw IllegalStateException("MovieRepository must be initialized")
        }
    }
}