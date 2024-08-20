package com.example.gamedoantu

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var wordTextView: TextView
    private lateinit var inputEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var skipButton: Button
    private lateinit var scoreTextView: TextView
    private var wordList = mutableListOf("computer", "kotlin", "android", "programming", "development", "keyboard", "mouse", "monitor", "laptop", "internet")
    private var currentWord: String = ""
    private var score: Int = 0
    private var wordIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordTextView = findViewById(R.id.wordTextView)
        inputEditText = findViewById(R.id.inputEditText)
        submitButton = findViewById(R.id.submitButton)
        skipButton = findViewById(R.id.skipButton)
        scoreTextView = findViewById(R.id.scoreTextView)

        startNewGame()

        submitButton.setOnClickListener {
            checkWord()
        }

        skipButton.setOnClickListener {
            skipWord()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun startNewGame() {
        score = 0
        wordIndex = 0
        scoreTextView.text = "Score: $score"
        getNextWord()
    }

    private fun getNextWord() {
        if (wordIndex < wordList.size) {
            currentWord = wordList[wordIndex]
            wordTextView.text = scrambleWord(currentWord)
            inputEditText.text.clear()
        } else {
            showWinDialog()
        }
    }

    private fun scrambleWord(word: String): String {
        val scrambledWord = word.toCharArray()
        scrambledWord.shuffle(Random(System.currentTimeMillis()))
        return String(scrambledWord)
    }

    private fun checkWord() {
        val inputWord = inputEditText.text.toString()
        if (inputWord.equals(currentWord, ignoreCase = true)) {
            score++
            scoreTextView.text = "Score: $score"
            if (score == 10) {
                showWinDialog()
            } else {
                wordIndex++
                getNextWord()
            }
        } else {
            Toast.makeText(this, "Chưa Đúng! Hãy Thử Lại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun skipWord() {
        wordIndex++
        getNextWord()
    }

    private fun showWinDialog() {
        AlertDialog.Builder(this)
            .setTitle("Chúc Mừng!")
            .setMessage("Bạn đã đoán đúng thành công 10 từ!")
            .setPositiveButton("Chơi Lại") { _, _ ->
                startNewGame()
            }
            .setNegativeButton("Thoát") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
}
