package com.example.medtracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var medCheckBox: CheckBox
    private lateinit var dateTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize views
        medCheckBox = findViewById(R.id.medCheckBox)
        dateTextView = findViewById(R.id.dateTextView)
        
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MedTracker", Context.MODE_PRIVATE)
        
        // Set up the date display
        updateDateDisplay()
        
        // Load today's medication status
        loadTodayStatus()
        
        // Set up checkbox listener
        medCheckBox.setOnCheckedChangeListener { _, isChecked ->
            saveTodayStatus(isChecked)
        }
    }
    
    private fun updateDateDisplay() {
        val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        dateTextView.text = currentDate
    }
    
    private fun getTodayKey(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
    
    private fun loadTodayStatus() {
        val todayKey = getTodayKey()
        val isTaken = sharedPreferences.getBoolean(todayKey, false)
        medCheckBox.isChecked = isTaken
    }
    
    private fun saveTodayStatus(isTaken: Boolean) {
        val todayKey = getTodayKey()
        val editor = sharedPreferences.edit()
        editor.putBoolean(todayKey, isTaken)
        editor.apply()
    }
}