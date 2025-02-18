package com.example.myshows

import androidx.lifecycle.ViewModel

class MovieListViewModel: ViewModel() {

    private val movieRepository= MovieRepository.get()
    var movieListLiveData= movieRepository.getMovies()
    fun getMoviesWatching(){
        movieListLiveData=movieRepository.getMoviesWatching()
    }
    fun getMovies(){
        movieListLiveData= movieRepository.getMovies()
    }
    fun getMoviesWillWatching(){
        movieListLiveData=movieRepository.getMoviesWillWatching()
    }
    fun getMoviesViewed(){
        movieListLiveData=movieRepository.getMoviesViewed()
    }
    fun getMoviesAbandoned(){
        movieListLiveData=movieRepository.getMoviesAbandoned()
    }

}