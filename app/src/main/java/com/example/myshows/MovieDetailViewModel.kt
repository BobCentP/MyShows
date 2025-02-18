package com.example.myshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import java.util.UUID

class MovieDetailViewModel(): ViewModel() {
    private val movieRepository= MovieRepository.get()
    private val movieIdLiveData= MutableLiveData<UUID>()

    var movieLiveData: LiveData<Movie?> =
        movieIdLiveData.switchMap { movieId-> movieRepository.getMovie(movieId)}

    fun loadMovie(movieId: UUID){
        movieIdLiveData.value = movieId
    }
    fun saveMovie(movie: Movie){
        movieRepository.updateMovie(movie)
    }


}