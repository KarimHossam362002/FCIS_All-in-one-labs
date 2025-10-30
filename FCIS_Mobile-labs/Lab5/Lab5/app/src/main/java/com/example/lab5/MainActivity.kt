package com.example.lab5

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val movieName: EditText = findViewById( R.id.etMovieName)
        val btnSave: Button = findViewById( R.id.btnSaveMovie)
        val btnShow: Button = findViewById( R.id.btnShowAllMovies)
        val db = MovieDatabase.getInstance( this)

        btnSave.setOnClickListener {
            val name = movieName.text.toString().trim()
            if (name.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    db.movieDao().insertMovie(Movie( name = name))
                    withContext( Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Movie saved!", Toast.LENGTH_SHORT).show()
                        movieName.text.clear()
                    }
                }
            } else {
                Toast.makeText( this, "Enter a movie name",  Toast.LENGTH_SHORT).show()
            }
        }

        btnShow.setOnClickListener {
            startActivity(Intent( this,  AllMoviesActivity::class.java))
        }
    }
}