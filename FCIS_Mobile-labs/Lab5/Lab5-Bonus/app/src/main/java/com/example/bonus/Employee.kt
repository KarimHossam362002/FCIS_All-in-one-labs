package com.example.bonus

import androidx.room.*

@Entity(
    tableName = "employees",
    foreignKeys = [
        ForeignKey(
            entity = Department::class,
            parentColumns = ["id"],
            childColumns = ["departmentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [Index("departmentId")]
)
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val departmentId: Int
)
