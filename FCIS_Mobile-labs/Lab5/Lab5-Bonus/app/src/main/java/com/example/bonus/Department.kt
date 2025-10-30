package com.example.bonus

import androidx.room.*


@Entity(tableName = "departments")
data class Department(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
