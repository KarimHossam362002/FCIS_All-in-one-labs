package com.example.lab5

import android.content.Intent
import android.os.Bundle // Import Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditMovieActivity : AppCompatActivity() {

    // Declare views and DB. We will initialize them in onCreate.
    private lateinit var editName: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnBack: Button
    private lateinit var btnActors: Button
    private lateinit var db: MovieDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)


        editName = findViewById<EditText>(R.id.editTextText)
        btnUpdate = findViewById<Button>(R.id.button_update)
        btnDelete = findViewById<Button>(R.id.button_delete)
        btnBack = findViewById<Button>(R.id.btnBack2)
        btnActors = findViewById<Button>(R.id.button_show_actors)


        db = MovieDatabase.getInstance(this)
        val movieId = intent.getIntExtra("movieId", 0)
        val movieName = intent.getStringExtra("movieName")


        editName.setText(movieName)


        btnUpdate.setOnClickListener {
            val newName = editName.text.toString().trim()
            if (newName.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {

                    db.movieDao().updateMovie(Movie(id = movieId, name = newName))

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@EditMovieActivity, "Movie updated!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {

                Toast.makeText(this@EditMovieActivity, "Name cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }
        btnDelete.setOnClickListener {
            lifecycleScope.launch(context = Dispatchers.IO) {
                db.movieDao().deleteMovie(Movie(id = movieId, name = editName.text.toString()))
                withContext(context = Dispatchers.Main) {
                    Toast.makeText(
                        this@EditMovieActivity,
                         "Movie deleted!",
                         Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }

        btnActors.setOnClickListener {
            val intent = Intent(this, ActorsActivity::class.java)
            intent.putExtra("movieId",  movieId)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            finish()
        }

    }
}