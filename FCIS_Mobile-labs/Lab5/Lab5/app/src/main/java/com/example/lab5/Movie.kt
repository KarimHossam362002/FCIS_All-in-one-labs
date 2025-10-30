package com.example.lab5


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true) val id:Int =0,
    val name: String
)
