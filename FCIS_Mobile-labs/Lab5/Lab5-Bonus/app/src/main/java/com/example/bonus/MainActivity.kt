package com.example.bonus

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "DB_TEST"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(this)
        val departmentDao = db.departmentDao()
        val employeeDao = db.employeeDao()
        lifecycleScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Inserting data")
            departmentDao.insert(Department(name = "IT"))
            departmentDao.insert(Department( name = "HR"))
            employeeDao.insert(Employee( name = "Ahmed", departmentId = 1))
            employeeDao.insert(Employee( name = "Sara", departmentId = 1))

            employeeDao.insert(Employee(name = "Dr.Omar", departmentId = 2))
            employeeDao.insert(Employee(name = "Pierre", departmentId = 2))

            val departments = departmentDao.getAllDepartments()
            Log.d(TAG, "Departments: $departments")

            val employeesInDept_1 = employeeDao.getEmployeesForDepartment(1)
            Log.d(TAG, "Employees in dept 1: $employeesInDept_1")

            val employeesInDept_2 = employeeDao.getEmployeesForDepartment(2)
            Log.d(TAG, "Employees in dept 2: $employeesInDept_2")
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}