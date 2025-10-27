package com.example.lab4

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val Lily = Plant("Lily","plant",3,5)
        val Tulip = Plant("Tulip","plant",3,10)
        val Rose = Plant("Rose","plant",1,7)

        val Plants = ArrayList<Plant>()
        Plants.add(Lily)
        Plants.add(Tulip)
        Plants.add(Rose)
        val filtered = Plants.asSequence().filter{it.name[0]=='R'}
        val newList = filtered.toList()

        Log.d("HandsOnTag","Plant Name : + ${newList.get(0).name}")
        Log.d("HandsOnTag","Plant Status : + ${newList.get(0).absorbWater(

            {newList.get(0).isAlive()}
        )
        }")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}