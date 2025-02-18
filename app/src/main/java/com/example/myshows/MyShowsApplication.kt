package com.example.myshows

import android.app.Application

class MyShowsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        MovieRepository.initialize(this)
    }
}