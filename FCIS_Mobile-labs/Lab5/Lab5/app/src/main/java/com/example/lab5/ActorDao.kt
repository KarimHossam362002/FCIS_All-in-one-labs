package com.example.lab5

import androidx.room.*


@Dao
interface ActorDao {
    @Insert
    suspend fun insertActor(actor: Actor)

    @Query("SELECT * FROM actors where movieId = :movieId")
    suspend fun getActorsForMovie(movieId: Int): List<Actor>
}