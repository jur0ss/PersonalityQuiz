package com.example.personalityquiz

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val season = intent.getStringExtra("season") ?: "Brak"
        val hobbies = intent.getStringArrayListExtra("hobbies") ?: arrayListOf()
        val seekValue = intent.getIntExtra("seekBarValue", 0)
        val color = intent.getStringExtra("color") ?: "Brak"
        val date = intent.getStringExtra("date") ?: "Brak"
        val time = intent.getStringExtra("time") ?: "Brak"

        val summaryText = """
            Wybrana pora roku: $season
            
            Zainteresowania: ${hobbies.joinToString(", ")}
            
            Poziom emocji (seekBar): $seekValue
            
            Ulubiony kolor: $color
            
            Wybrana data: $date
            
            Wybrany czas: $time
        """.trimIndent()

        val summaryTextView = findViewById<TextView>(R.id.summaryTextView)
        summaryTextView.text = summaryText
    }
}