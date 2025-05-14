package com.example.personalityquiz

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val radioGroup: RadioGroup = findViewById(R.id.seasons_radiogroup)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedSeason = when (checkedId) {
                R.id.spring_radio -> "Wiosna"
                R.id.summer_radio -> "Lato"
                R.id.autumn_radio -> "Jesień"
                else -> "Zima"
            }

        }

        val datePicker: DatePicker = findViewById(R.id.datepicker)
        datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
        }

        val timePicker = findViewById<TimePicker>(R.id.timepicker)
        timePicker.setIs24HourView(true)

        timePicker.setOnTimeChangedListener { view, hour, minute ->
            val selectedTime = "$hour:$minute"
        }

        var reading = findViewById<CheckBox>(R.id.reading_check)
        var drawing = findViewById<CheckBox>(R.id.drawing_check)
        var sport = findViewById<CheckBox>(R.id.sport_check)
        var computer = findViewById<CheckBox>(R.id.computer_check)
        var nature = findViewById<CheckBox>(R.id.nature_check)

        var selectedOptions = mutableListOf<String>()





    }
}