package net.eucalypto.geoquiz

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_CURRENT_INDEX = "current_index"
private const val KEY_IS_CHEATER = "has_cheated"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button

    private val viewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate() called")
        Log.d(TAG, "got this model to work with: $viewModel")

        restoreInstanceState(savedInstanceState)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        cheatButton = findViewById(R.id.cheat_button)


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

        cheatButton.setOnClickListener {
            val answerIsTrue = viewModel.currentQuestionAnswer
            val intent = CheatActivity.createIntent(this, answerIsTrue)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Log.d(TAG, "activating new cool API 23 feature of transition animation")
                val options = ActivityOptions.makeClipRevealAnimation(it, 0, 0, it.width, it.height)
                startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
            } else {
                startActivityForResult(intent, REQUEST_CODE_CHEAT)
            }
        }
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            viewModel.currentIndex = it.getInt(KEY_CURRENT_INDEX)
            viewModel.isCheater = it.getBoolean(KEY_IS_CHEATER)
            Log.d(TAG, "restored state from savedInstanceState")
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (viewModel.isCheater) {
                return // stay cheater
            }
            viewModel.isCheater = data?.getBooleanExtra(KEY_EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_INDEX, viewModel.currentIndex)
        outState.putBoolean(KEY_IS_CHEATER, viewModel.isCheater)
        Log.d(TAG, "save state in onSaveInstanceState()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = viewModel.currentQuestionAnswer
        val isCorrect = (userAnswer == correctAnswer)
        val toastTextId = when {
            viewModel.isCheater -> R.string.judgment_toast
            isCorrect -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, toastTextId, Toast.LENGTH_SHORT).show()
    }

    private fun updateQuestion() {
        val questionTextResId = viewModel.currentQuestionTextResId
        questionTextView.setText(questionTextResId)
    }
}