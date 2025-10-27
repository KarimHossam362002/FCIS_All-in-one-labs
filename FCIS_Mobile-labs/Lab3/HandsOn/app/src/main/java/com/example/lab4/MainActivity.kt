package com.example.lab4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //val receivedText = ""
        val tv_message1 = findViewById<TextView>(R.id.tv_message1)
        val btn_send = findViewById<Button>(R.id.btn_send)
        val intent = intent
        if(intent.action == Intent.ACTION_SEND && intent.type == "text/plain"){
            val receivedText = intent.getStringExtra(Intent.EXTRA_TEXT)
            tv_message1.text = receivedText
        }
        btn_send.setOnClickListener {
            val intent2 = Intent(this, MainActivity2::class.java)
            val message = tv_message1.text.toString().trim()
            intent2.putExtra("message",message)
            startActivity(intent2)
        }

    }
}