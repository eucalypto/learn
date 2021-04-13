package net.eucalypto.geoquiz

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private val questionsBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            currentIndex = (currentIndex + 1) % questionsBank.size
            updateQuestion()
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionsBank[currentIndex].answer
        val isCorrect = (userAnswer == correctAnswer)
        val toastTextId = if (isCorrect) R.string.correct_toast else R.string.incorrect_toast
        Toast.makeText(this, toastTextId, Toast.LENGTH_SHORT).show()
    }


    private fun updateQuestion() {
        val questionTextResId = questionsBank[currentIndex].textResID
        questionTextView.setText(questionTextResId)
    }
}