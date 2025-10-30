package com.example.bonus

import androidx.room.*

@Dao
interface EmployeeDao {


    @Insert
    suspend fun insert(employee: Employee)

    @Query("SELECT * FROM employees WHERE departmentId = :deptId")
    suspend fun getEmployeesForDepartment(deptId: Int): List<Employee>
}