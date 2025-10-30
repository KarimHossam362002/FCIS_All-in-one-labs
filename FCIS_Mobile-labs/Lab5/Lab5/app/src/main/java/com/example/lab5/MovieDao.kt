package com.example.lab5

import androidx.room.*

@Dao
interface MovieDao {
    @Insert
    suspend fun insertMovie(movie: Movie)

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies():List<Movie>
    @Update
    suspend fun updateMovie(movie: Movie)
    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movies")
    suspend fun getMoviesWithActors():List<MovieWithActors>
}