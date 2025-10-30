package com.example.lab5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class,Actor::class], version = 1)

abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun actorDao(): ActorDao

    companion object{
        @Volatile
        private var INSTANCE: MovieDatabase?=null

        fun getInstance(context: Context): MovieDatabase{
            if (INSTANCE==null){

                synchronized(MovieDatabase::class){
                    INSTANCE= Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java, "movie_db").build()
                }

            }
            return INSTANCE!! // what is !! ?
        }
    }
}