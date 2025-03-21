package com.example.myshows

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID
import java.util.concurrent.Executors

private const val TAG = "MovieListFragment"

class MovieListFragment(private var status: Int): Fragment() {

    interface Callbacks{
        fun onMovieSelected(movieId: UUID)
    }

    private var callBacks: Callbacks? = null
    private lateinit var movieRecyclerView: RecyclerView
    private var adapter: MovieAdapter?=MovieAdapter(emptyList())


    private val movieListViewModel: MovieListViewModel by lazy {
        ViewModelProvider(this)[MovieListViewModel::class.java]
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.fragment_movie_list, menu)
//
//    }
    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return true
    }*/

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBacks= context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        movieListViewModel.getMovies(status)

    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)

        movieRecyclerView = view.findViewById<RecyclerView>(R.id.movie_recycler_view)!!
        movieRecyclerView.layoutManager = GridLayoutManager(context,2)
        movieRecyclerView.adapter=adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        activity?.title=(when(status){
            1->resources.getString(R.string.movie_status_watching)
            2->resources.getString(R.string.movie_status_willWatching)
            3->resources.getString(R.string.movie_status_viewed)
            4->resources.getString(R.string.movie_status_abandoned)
            else->resources.getString(R.string.movie_status_notViewed)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restartObserver()
    }

    override fun onDetach() {
        super.onDetach()
        callBacks=null
    }



    private fun updateUI(movies: List<Movie>){
        adapter = MovieAdapter(movies)
        movieRecyclerView.adapter = adapter
    }

    private inner class MovieHolder(view:View): RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var movie: Movie
        private val moviePosterView: ImageView = itemView.findViewById(R.id.image_movie_poster)
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        init{
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie){
            this.movie = movie

            executor.execute {
                try {
                    val `in` = java.net.URL(this.movie.image).openStream()
                    image = BitmapFactory.decodeStream(`in`)

                    handler.post { moviePosterView.setImageBitmap(image)}

                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

        override fun onClick(v: View) {
            callBacks?.onMovieSelected(movie.id)
        }
    }

    private inner class MovieAdapter(var movies: List<Movie>): RecyclerView.Adapter<MovieHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
            val view= layoutInflater.inflate(R.layout.list_item_movie, parent,false)
            return MovieHolder(view)
        }

        override fun getItemCount() = movies.size

        override fun onBindViewHolder(holder: MovieHolder, position: Int) {
            val movie = movies[position]
            holder.bind(movie)
        }
    }

    companion object{
        fun newInstance(): MovieListFragment{
            return MovieListFragment(0)
        }
    }
    private fun restartObserver(){
        movieListViewModel.movieListLiveData.observe(
            viewLifecycleOwner,
            Observer { movies->
                movies?.let {
                    updateUI(movies)
                }
            }
        )
    }
}