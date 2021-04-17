package net.eucalypto.geoquiz

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_CURRENT_INDEX = "current_index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private val viewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate() called")

        savedInstanceState?.let {
            viewModel.currentIndex = it.getInt(KEY_CURRENT_INDEX)
            Log.d(TAG, "restored state from savedInstanceState")
        }


        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)


        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        updateQuestion()

        nextButton.setOnClickListener {
            viewModel.moveToNextQuestion()
            updateQuestion()
        }

        Log.d(TAG, "got this model to work with: $viewModel")


    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_INDEX, viewModel.currentIndex)
        Log.d(TAG, "save state in onSaveInstanceState()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = viewModel.currentQuestionAnswer
        val isCorrect = (userAnswer == correctAnswer)
        val toastTextId = if (isCorrect) R.string.correct_toast else R.string.incorrect_toast
        Toast.makeText(this, toastTextId, Toast.LENGTH_SHORT).show()
    }


    private fun updateQuestion() {
        val questionTextResId = viewModel.currentQuestionTextResId
        questionTextView.setText(questionTextResId)
    }
}