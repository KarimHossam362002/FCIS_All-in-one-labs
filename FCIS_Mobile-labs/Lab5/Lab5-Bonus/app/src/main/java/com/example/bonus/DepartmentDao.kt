package com.example.bonus

import androidx.room.*

@Dao
interface DepartmentDao {

    @Insert
    suspend fun insert(department: Department)

    @Query("SELECT * FROM departments")
    suspend fun getAllDepartments(): List<Department>
}