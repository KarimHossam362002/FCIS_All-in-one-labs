package com.example.listview

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val items = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        savedInstanceState?.getStringArrayList("items")?.let{
            items.clear()
            items.addAll(it)
        }
        Log.d(TAG,"On Create Activity 1")

        val et_names = findViewById<EditText>(R.id.et_name)
        val btn_add = findViewById<Button>(R.id.btn_add)

        val lst_items = findViewById<ListView>(R.id.lst_names)
        var adapter: ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1,items)
        lst_items.adapter = adapter

        btn_add.setOnClickListener() {
            Toast.makeText(this,"HELLO WORLD!",Toast.LENGTH_LONG).show()
            val names = et_names.toString().trim()
            if(names.isEmpty())
                Toast.makeText(this,"PLEASE FILL NAMES",Toast.LENGTH_LONG).show()
            else{
                items.add(names)
                adapter.notifyDataSetChanged()
            }

        }


    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("items", ArrayList(items))
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG,"On Start Activity 1")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"On Resume Activity 1")
    }
    override fun onPause(){
        super.onPause()
        Log.d(TAG,"On Pause Activity 1")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"On Stop Activity 1")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"On Destroy Activity 1")
    }
}