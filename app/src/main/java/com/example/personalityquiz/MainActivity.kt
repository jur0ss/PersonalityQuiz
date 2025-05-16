package com.example.personalityquiz

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var chronometer: Chronometer
    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button
    private lateinit var resetBtn: Button
    private var running = false
    private var pauseOffset: Long = 0

    private lateinit var selectedSeason: String
    private lateinit var timerTextView: TextView

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


        chronometer = findViewById(R.id.chronometer)


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
                chronometer.stop()
                running = false
            }
        }

        resetBtn.setOnClickListener {
            chronometer.base = SystemClock.elapsedRealtime()
            pauseOffset = 0
            chronometer.stop()
            running = false
        }


        val radioGroup: RadioGroup = findViewById(R.id.seasons_radiogroup)
        selectedSeason = "Wiosna" // domyślnie
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedSeason = when (checkedId) {
                R.id.spring_radio -> "Wiosna"
                R.id.summer_radio -> "Lato"
                R.id.autumn_radio -> "Jesień"
                else -> "Zima"
            }
        }


        val datePicker: DatePicker = findViewById(R.id.datepicker)


        val timePicker = findViewById<TimePicker>(R.id.timepicker)
        timePicker.setIs24HourView(true)


        val seekBar = findViewById<SeekBar>(R.id.seekBar)


        val spinner: Spinner = findViewById(R.id.spinnerColours)
        val colours = arrayOf("Niebieski", "Czerwony", "Zielony", "Żółty")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, colours)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        timerTextView = findViewById(R.id.timerCounter)
        val countDownTimer = object : CountDownTimer(21000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                timerTextView.text = "Pozostało $secondsLeft sekund"
            }

            override fun onFinish() {
                timerTextView.text = "Czas minął!"
            }
        }
        countDownTimer.start()


        val btnOpenNewActivity: Button = findViewById(R.id.submit_button)
        btnOpenNewActivity.setOnClickListener {
            val selectedOptions = mutableListOf<String>()
            if (findViewById<CheckBox>(R.id.reading_check).isChecked) selectedOptions.add("Czytanie")
            if (findViewById<CheckBox>(R.id.drawing_check).isChecked) selectedOptions.add("Rysowanie")
            if (findViewById<CheckBox>(R.id.sport_check).isChecked) selectedOptions.add("Sport")
            if (findViewById<CheckBox>(R.id.computer_check).isChecked) selectedOptions.add("Granie na komputerze")
            if (findViewById<CheckBox>(R.id.nature_check).isChecked) selectedOptions.add("Natura")

            val seekValue = seekBar.progress
            val selectedColor = spinner.selectedItem.toString()
            val selectedDate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
            val selectedTime = "${timePicker.hour}:${timePicker.minute}"

            val intent = Intent(this, SummaryActivity::class.java).apply {
                putExtra("season", selectedSeason)
                putStringArrayListExtra("hobbies", ArrayList(selectedOptions))
                putExtra("seekBarValue", seekValue)
                putExtra("color", selectedColor)
                putExtra("date", selectedDate)
                putExtra("time", selectedTime)
            }

            startActivity(intent)
        }
    }
}