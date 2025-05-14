package com.example.personalityquiz

import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Chronometer
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)

    private lateinit var chronometer: Chronometer
    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button
    private lateinit var resetBtn: Button
    private var running = false
    private var pauseOffset: Long = 0



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

        chronometer = findViewById<Chronometer>(R.id.chronometer)
        startBtn = findViewById(R.id.startButton)
        stopBtn = findViewById(R.id.stopButton)
        resetBtn = findViewById(R.id.resetButton)


        startBtn.setOnClickListener {

            if (!running) {
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
                chronometer.start()
                running = true
            }
        }

        stopBtn.setOnClickListener {
            if (running) {
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
                Log.i("offset", "pauseOffset: $pauseOffset")
                chronometer.stop()
                running = false
            }
        }

        resetBtn.setOnClickListener {
            chronometer.base = SystemClock.elapsedRealtime()
            Log.i("offset", "base: ${chronometer.base}")
            pauseOffset = 0
            chronometer.stop()
            running = false
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

        if (reading.isChecked) selectedOptions.add("Czytanie")
        if (drawing.isChecked) selectedOptions.add("Rysowanie")
        if (sport.isChecked) selectedOptions.add("Sport")
        if (computer.isChecked) selectedOptions.add("Granie na komputerze")
        if (nature.isChecked) selectedOptions.add("Natura")


        var seekBar = findViewById<SeekBar>(R.id.seekBar)

        seekBar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var emotions = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        val spinner: Spinner = findViewById(R.id.spinnerColours)
        val colours = arrayOf("Niebieski", "Czerwony", "Zielony", "Żółty")

        val adapter = ArrayAdapter( this,
            android.R.layout.simple_list_item_1,
            colours
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedColour = colours[position]
                Toast.makeText(this@MainActivity, "Wybrałeś: $selectedColour", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }








    }
}