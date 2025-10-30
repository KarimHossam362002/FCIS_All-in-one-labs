package com.example.lab5

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "actors",
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movieId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Actor(
    @PrimaryKey(autoGenerate = true) val id:Int =0,
    val movieId:Int,
    val name:String
)
