package com.example.lab4

import android.util.Log

class Plant (var name:String , var type:String,var WateringInterval:Int , var LastWatered:Int){



    fun isAlive(): Boolean{
        if(LastWatered - WateringInterval >=5){
            return false
        }
        return true
    }
    fun absorbWater(isAlive:(Unit)->Boolean) : Unit {
        if(isAlive(Unit)){
            Log.d("HandsOnTag","test Watered :D ")
            LastWatered = 0
        }
        else{
            Log.d("HandsOnTag","Plant is dead ;-;")
        }
    }
}