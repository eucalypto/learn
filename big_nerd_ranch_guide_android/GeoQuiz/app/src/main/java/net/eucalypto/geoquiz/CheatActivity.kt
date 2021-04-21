package net.eucalypto.geoquiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val KEY_EXTRA_ANSWER_IS_TRUE = "net.eucalypto.geoquiz.answer_is_true"
const val KEY_EXTRA_ANSWER_SHOWN = "net.eucalypto.geoquiz.answer_shown"
private const val KEY_HAS_CHEATED = "has_cheated"

class CheatActivity : AppCompatActivity() {

    companion object {
        fun createIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(KEY_EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    private var answerIsTrue = false
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private var hasCheated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        restoreInstanceState(savedInstanceState)

        answerIsTrue = intent.getBooleanExtra(KEY_EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        showAnswerButton.setOnClickListener {
            answerTextView.setText(
                when {
                    answerIsTrue -> R.string.true_button
                    else -> R.string.false_button
                }
            )
            hasCheated = true
            setResultAnswerShown(hasCheated)
        }
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            hasCheated = it.getBoolean(KEY_HAS_CHEATED)
            setResultAnswerShown(hasCheated)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_HAS_CHEATED, hasCheated)
    }

    private fun setResultAnswerShown(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(KEY_EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(RESULT_OK, data)
    }
}