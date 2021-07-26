package com.example.android.unscramble.ui.game

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var score = 0
    private var currentWordCount = 0

    private lateinit var currentWord: String

    private val usedWords = mutableListOf<String>()

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord


    init {
        updateNextWord()
    }

    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            updateNextWord()
            true
        } else {
            false
        }
    }

    private fun updateNextWord() {
        updateNextUnusedWord()
        updateShuffledWord()
        updateWordStatistics()
    }

    private fun updateNextUnusedWord() {
        do {
            currentWord = allWordsList.random()
        } while (currentWord in usedWords)
    }

    private fun updateShuffledWord() {
        do {
            _currentScrambledWord = currentWord.shuffle()
        } while (currentScrambledWord == currentWord)
    }

    private fun updateWordStatistics() {
        currentWordCount++
        usedWords.add(currentWord)
    }
}

fun String.shuffle(): String {
    return String(this.toCharArray().apply { shuffle() })
}