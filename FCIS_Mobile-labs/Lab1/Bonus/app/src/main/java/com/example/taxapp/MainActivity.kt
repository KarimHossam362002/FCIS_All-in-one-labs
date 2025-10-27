package com.example.taxapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var editTextAmount: EditText
    private lateinit var textViewTaxRate: TextView
    private lateinit var seekBarTax: SeekBar
    private lateinit var buttonCalculate: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // initialization
         editTextAmount = findViewById(R.id.editTextTaxInput)
         textViewTaxRate = findViewById(R.id.textViewTaxRate)
         seekBarTax = findViewById(R.id.seekBarTax)
         buttonCalculate = findViewById(R.id.buttonTax)
         textViewResult = findViewById(R.id.TaxTextViewResult)

        val initialTaxRate = seekBarTax.progress
        textViewTaxRate.text = "Tax: $initialTaxRate%"

        seekBarTax.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                textViewTaxRate.text = "Tax: $progress%"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        buttonCalculate.setOnClickListener {
            calculateTax()
        }


    }
    private fun calculateTax(){
        val amountText = editTextAmount.text.toString()
        if (amountText.isEmpty()) {
            textViewResult.text = "Result will appear here"
            editTextAmount.error = "Please enter an amount"
            return
        }
        val baseAmount = amountText.toDouble()
        val taxRatePercent = seekBarTax.progress.toDouble()
        val taxAmount = baseAmount * (taxRatePercent / 100.0) //10
        val totalAmount = baseAmount + taxAmount // 100+10 = 110
     // formatting
        val resultText = "Tax: %.1f\nTotal: %.1f".format(taxAmount, totalAmount)
        textViewResult.text = resultText
    }
}