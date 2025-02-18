package com.example.myshows

import android.graphics.Bitmap
import android.graphics.BitmapFactory


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.UUID
import java.util.concurrent.Executors

private const val TAG= "MovieRepository"
private const val ARG_MOVIE_ID="movie_id"
class MovieFragment : Fragment() {

    private lateinit var movie: Movie
    private lateinit var titleField: TextView
    private lateinit var descriptionField: TextView
    private lateinit var imageField: ImageView
    private lateinit var buttonWatchingField: ImageButton
    private lateinit var buttonWillWatchingField: ImageButton
    private lateinit var buttonEveningField: ImageButton
    private lateinit var buttonAbandonedField:ImageButton
    private val movieDetailViewModel: MovieDetailViewModel by lazy {
        ViewModelProvider(this)[MovieDetailViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        movie = Movie()
        val movieId: UUID = arguments?.getSerializable(ARG_MOVIE_ID) as UUID
        movieDetailViewModel.loadMovie(movieId)

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"tyt oshibka")
        buttonWatchingField.setOnClickListener { movie.status=1 }
        buttonWillWatchingField.setOnClickListener {movie.status=2 }
        buttonEveningField.setOnClickListener { movie.status=3 }
        buttonAbandonedField.setOnClickListener { movie.status=4 }
    }
    override fun onStop() {
        super.onStop()
        movieDetailViewModel.saveMovie(movie)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        titleField=view.findViewById(R.id.movie_title) as TextView
        descriptionField= view.findViewById(R.id.movie_description) as TextView
        imageField= view.findViewById(R.id.image_movie_poster) as ImageView
        buttonWatchingField=view.findViewById(R.id.imageButton_watching) as ImageButton
        buttonWillWatchingField=view.findViewById(R.id.imageButton_willWatching) as ImageButton
        buttonEveningField=view.findViewById(R.id.imageButton_viewed) as ImageButton
        buttonAbandonedField=view.findViewById(R.id.imageButton_abandoned) as ImageButton
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDetailViewModel.movieLiveData.observe(
            viewLifecycleOwner,
            Observer { movie->
                movie?.let {
                    this.movie=movie
                    updateUI()
                }
            }
        )
    }



    private fun updateUI(){
        titleField.setText(movie.title)
        descriptionField.setText(movie.description)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        executor.execute {
            try {
                val `in` = java.net.URL(this.movie.image).openStream()
                image = BitmapFactory.decodeStream(`in`)

                handler.post { imageField.setImageBitmap(image)}

            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object{
        fun newInstance(movieId: UUID): MovieFragment{
            val args= Bundle().apply {
                putSerializable(ARG_MOVIE_ID, movieId)
            }
            return MovieFragment().apply {
                arguments=args
            }
        }
    }

}