package com.example.lab5

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllMoviesActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var btnBack: Button
    private lateinit var db: MovieDatabase
    private lateinit var movies: MutableList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_movies)

        listView = findViewById(R.id.listViewMovies)
        btnBack = findViewById( R.id.btnBack)
        db = MovieDatabase.getInstance( this)

        loadMovies()

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadMovies() {
        lifecycleScope.launch(Dispatchers.IO) {
            movies = db.movieDao().getAllMovies().toMutableList()

            withContext( Dispatchers.Main) {
                val adapter = ArrayAdapter(
                    this@AllMoviesActivity,android.R.layout.simple_list_item_1, movies.map { it.name }
                )

                listView.adapter = adapter

                listView.setOnItemClickListener { _, _, position, _ ->
                    val selectedMovie = movies[position]
                    val intent = Intent( this@AllMoviesActivity,
                         EditMovieActivity::class.java)
                    intent.putExtra( "movieId", selectedMovie.id)
                    intent.putExtra( "movieName", selectedMovie.name)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        loadMovies()
    }
}