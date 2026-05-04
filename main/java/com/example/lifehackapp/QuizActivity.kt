package com.example.lifehackapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {
    var index = 0
    var score = 0
    lateinit var questionText: TextView
    lateinit var feedbackText: TextView
    val questions = arrayOf(
        "Turning off Bluetooth when not in use saves battery",
        "More RAM always makes a phone faster in every situation",
        "Using a phone while charging can slow down charging speed",
        "Deleting apps you don’t use can improve performance",
        "Screen brightness affects battery life"
    )

    val answers = arrayOf(true, false, true, true, true)
    val explanations = arrayOf(
        "Bluetooth uses power to stay connected or search for devices, so turning it off saves battery.",
        "RAM helps with multitasking, but overall speed also depends on the processor, storage, and software optimization.",
        "When you use your phone while charging, it consumes power at the same time, which slows down how fast it charges.",
        "Unused apps can take up storage and sometimes run in the background, so removing them can improve performance.",
        "Higher screen brightness uses more power, which drains the battery faster."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quize)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        questionText = findViewById(R.id.questionText)
        feedbackText = findViewById(R.id.feedbackText)

        val hackButton = findViewById<Button>(R.id.hackButton)
        val mythButton = findViewById<Button>(R.id.mythButton)
        val nextButton = findViewById<Button>(R.id.nextButton)

        loadQuestion()

        hackButton.setOnClickListener { checkAnswer(true) }
        mythButton.setOnClickListener { checkAnswer(false) }

        nextButton.setOnClickListener {
            index++

            if (index < questions.size) {
                loadQuestion()
                feedbackText.text = ""
            } else {
                val intent = Intent(this, ScoreActivity::class.java)
                intent.putExtra("score", score)
                intent.putExtra("total", questions.size)
                startActivity(intent)
                finish()
            }
        }
    }

    fun loadQuestion() {
        questionText.text = questions[index]
    }

    fun checkAnswer(userAnswer: Boolean) {
        if (userAnswer == answers[index]) {
            feedbackText.text = "Correct! 🎉\n${explanations[index]}"
            score++
        } else {
            feedbackText.text = "Wrong! ❌\n${explanations[index]}"
        }
    }
}
