package com.example.bonus

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * The main Room database class for the application.
 * It lists all entities and provides access to all DAOs.
 *
 * From image_ca3d9c.png
 */
@Database(entities = [Department::class, Employee::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    abstract fun departmentDao(): DepartmentDao
    abstract fun employeeDao(): EmployeeDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            if(INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_db").build()
                }
            }
            return INSTANCE!! // what is !! ????
        }
    }
}
