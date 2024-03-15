package com.example.codemath

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var simpleEditText: EditText
    private lateinit var percentageTextView: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var tipTextView: TextView
    private lateinit var totalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        simpleEditText = findViewById(R.id.et_simple)
        percentageTextView = findViewById(R.id.tv_percentage)
        seekBar = findViewById(R.id.seekBar)
        tipTextView = findViewById(R.id.tipAmount)
        totalTextView = findViewById(R.id.totalAmount)

        // Set initial values for TextViews
        tipTextView.text = NumberFormat.getCurrencyInstance().format(0)
        totalTextView.text = NumberFormat.getCurrencyInstance().format(0)

        // Set a listener on the SeekBar to update the TextView and calculate tip when the SeekBar value changes
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Display the current percentage
                percentageTextView.text = "$progress%"
                // Calculate and display the tip and total based on the entered base amount
                calculateAndDisplay()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Nothing needed here
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Nothing needed here
            }
        })

        // Set a listener on the EditText to recalculate the tip whenever the user edits the base amount
        simpleEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Nothing needed here
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Calculate and display the tip and total whenever the text changes
                calculateAndDisplay()
            }

            override fun afterTextChanged(s: Editable?) {
                // Nothing needed here
            }
        })
    }

    private fun calculateAndDisplay() {
        // Get the base amount from the EditText
        val baseAmountText = simpleEditText.text.toString()
        if (baseAmountText.isNotEmpty()) {
            val baseAmount = baseAmountText.toDouble()
            // Get the current tip percentage from the SeekBar
            val tipPercentage = seekBar.progress

            // Calculate the tip and total amounts
            val tipAmount = baseAmount * (tipPercentage / 100.0)
            val totalAmount = baseAmount + tipAmount

            // Display the formatted tip and total amounts
            tipTextView.text = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(tipAmount)
            totalTextView.text = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(totalAmount)
        } else {
            // If the base amount is empty, clear the tip and total TextViews
            tipTextView.text = ""
            totalTextView.text = ""
        }
    }
}

