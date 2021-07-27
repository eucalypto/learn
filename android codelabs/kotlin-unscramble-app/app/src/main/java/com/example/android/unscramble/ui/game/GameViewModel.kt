package com.example.android.unscramble.ui.game

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _hasGuessedCorrectly = true
    val hasGuessedCorrectly get() = _hasGuessedCorrectly

    private var _score = 0
    val score get() = _score

    private var _currentWordCount = 0
    val currentWordCount get() = _currentWordCount

    private lateinit var currentWord: String

    private val usedWords = mutableListOf<String>()

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord
        get() = _currentScrambledWord

    private var _gameIsFinished = false
    val gameIsFinished get() = _gameIsFinished


    init {
        updateNextWord()
    }

    fun checkUserInput(userInput: String) {
        if (userInput == currentWord) {
            _score += SCORE_INCREASE
            _hasGuessedCorrectly = true
        } else {
            _hasGuessedCorrectly = false
        }
    }

    fun nextWord() {
        updateGameIsFinished()
        if (gameIsFinished) return
        updateNextWord()
    }

    private fun updateGameIsFinished() {
        if (currentWordCount >= MAX_NO_OF_WORDS) {
            _gameIsFinished = true
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
        _currentWordCount++
        usedWords.add(currentWord)
    }

    fun resetGame() {
        _score = 0
        _currentWordCount = 0
        usedWords.clear()
        _gameIsFinished = false
        updateNextWord()
    }
}

fun String.shuffle(): String {
    return String(this.toCharArray().apply { shuffle() })
}