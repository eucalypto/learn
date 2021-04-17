package net.eucalypto.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    private val questionsBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    var currentIndex = 0

    val currentQuestionAnswer: Boolean
        get() = questionsBank[currentIndex].answer

    val currentQuestionTextResId: Int
        get() = questionsBank[currentIndex].textResID

    fun moveToNextQuestion() {
        currentIndex = (currentIndex + 1) % questionsBank.size
    }


    init {
        Log.d(TAG, "instance of QuizViewModel has been created: $this")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "instance of QuizViewModel will be destroyed: $this")
    }
}