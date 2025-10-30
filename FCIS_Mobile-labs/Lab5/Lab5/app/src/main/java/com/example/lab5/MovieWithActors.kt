package com.example.lab5

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithActors(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val actors:List<Actor>
)
