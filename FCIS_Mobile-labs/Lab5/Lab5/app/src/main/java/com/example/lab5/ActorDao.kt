package com.example.lab5

import androidx.room.Insert
import androidx.room.Query

interface ActorDao {
    @Insert
    suspend fun insertActor(actor: Actor)

    @Query("SELECT * FROM actors where movieId = :movieId")
    suspend fun getActorsForMovie(movieId: Int): List<Actor>
}