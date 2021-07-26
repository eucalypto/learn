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
        getNextWord()
    }

    fun getNextWord() {
        do {
            currentWord = allWordsList.random()
        } while (currentWord in usedWords)

        val scrambled = currentWord.toCharArray()
        do {
            scrambled.shuffle()
        } while (String(scrambled) == currentWord)

        _currentScrambledWord = String(scrambled)
        currentWordCount++
        usedWords.add(currentWord)
    }
}