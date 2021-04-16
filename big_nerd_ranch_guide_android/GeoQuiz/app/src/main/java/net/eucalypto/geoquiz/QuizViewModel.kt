package net.eucalypto.geoquiz

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

    private var currentIndex = 0

    val currentQuestionAnswer: Boolean
        get() = questionsBank[currentIndex].answer

    val currentQuestionTextResId: Int
        get() = questionsBank[currentIndex].textResID

    fun moveToNextQuestion() {
        currentIndex = (currentIndex + 1) % questionsBank.size
    }
}