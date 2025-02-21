package com.example.myshows

import androidx.lifecycle.ViewModel

class MovieListViewModel(): ViewModel() {

    private val movieRepository= MovieRepository.get()
    var movieListLiveData= movieRepository.getMovies(0)
    fun getMovies(status:Int){
        movieListLiveData= movieRepository.getMovies(status)
    }



}