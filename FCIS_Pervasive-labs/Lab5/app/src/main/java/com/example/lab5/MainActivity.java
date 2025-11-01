package com.example.lab5;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        final EditText edit_text_movie_name = findViewById(R.id.edit_text_movie_name);
        final EditText edit_text_movie_description = findViewById(R.id.edit_text_movie_description);
        final Button button_add_movie = findViewById(R.id.button_add_movie);
        final Button button_show_all_movies = findViewById(R.id.button_show_all_movies);

        final MovieDBHelper newMovie = new MovieDBHelper(this);

        button_add_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMovie.insertMovie(edit_text_movie_name.getText().toString(),edit_text_movie_description.getText().toString());
                Toast.makeText(MainActivity.this, "Movie Added", Toast.LENGTH_LONG).show();

            }
        });
        button_show_all_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allMovies = new Intent(MainActivity.this, ShowMoviesActivity.class);
                startActivity(allMovies);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}