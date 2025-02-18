package com.example.myshows

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import java.util.UUID

class MainActivity : AppCompatActivity(), MovieListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        title=""

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment==null){

            val fragment = MovieListFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()

        }
    }

    override fun onMovieSelected(movieId: UUID) {
        val fragment = MovieFragment.newInstance(movieId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}