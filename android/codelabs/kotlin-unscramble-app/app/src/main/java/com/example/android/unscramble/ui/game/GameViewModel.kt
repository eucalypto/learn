package com.example.android.unscramble.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _hasGuessedCorrectly = true
    val hasGuessedCorrectly get() = _hasGuessedCorrectly

    private val _score = MutableLiveData(0)
    val score get() = _score as LiveData<Int>

    private var _currentWordCount = MutableLiveData(0)
    val currentWordCount get() = _currentWordCount as LiveData<Int>

    private lateinit var currentWord: String

    private val usedWords = mutableListOf<String>()

    private val _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    private var _gameIsFinished = false
    val gameIsFinished get() = _gameIsFinished


    init {
        updateNextWord()
    }

    fun checkUserInput(userInput: String) {
        if (userInput.equals(currentWord, ignoreCase = true)) {
            _score.value = score.value?.plus(SCORE_INCREASE)
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
        currentWordCount.value?.let {
            if (it >= MAX_NO_OF_WORDS) _gameIsFinished = true
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
            _currentScrambledWord.value = currentWord.shuffle()
        } while (currentScrambledWord.value == currentWord)
    }

    private fun updateWordStatistics() {
        _currentWordCount.value = _currentWordCount.value?.inc()
        usedWords.add(currentWord)
    }

    fun resetGame() {
        _score.value = 0
        _currentWordCount.value = 0
        usedWords.clear()
        _gameIsFinished = false
        updateNextWord()
    }
}

fun String.shuffle(): String {
    return String(this.toCharArray().apply { shuffle() })
}