package com.example.lab5;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowMoviesActivity extends AppCompatActivity {
    ListView movieList;
    ArrayAdapter<String> moviesAdapter;
    MovieDBHelper movies;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movies);
        movieList = (ListView) findViewById(R.id.movie_list_view);
        moviesAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        movieList.setAdapter(moviesAdapter);
        movies = new MovieDBHelper(getApplicationContext());
        Cursor cursor = movies.fetchAllData();
        while (!cursor.isAfterLast()) {
            moviesAdapter.add(cursor.getString(0));
            cursor.moveToNext();


        }
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_description = (TextView) findViewById(R.id.tv_description);
                String description = movies.getMovieDesc(((TextView)view).getText().toString());
                tv_description.setText(description);
            }
        });
        
    }
}