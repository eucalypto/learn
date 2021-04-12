package net.eucalypto.layouts

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_views_examples)
    }

    fun onTextViewClicked(view: View) {
        Toast
            .makeText(
                this,
                getString(R.string.text_view_clicked_toast),
                Toast.LENGTH_SHORT
            )
            .show()
        (view as TextView).text = getString(R.string.example_text_view_after_click)
    }

    fun onEditTextClicked(view: View) {
        val input = (view as EditText).text.toString()

        Toast.makeText(this, "You just typed: \"$input\"", Toast.LENGTH_LONG).show()
    }

    fun onToggleButtonClicked(view: View) {
        val toggled = (view as ToggleButton).isChecked

        if (toggled) {
            toast("You just turned the ToggleButton on")
        } else {
            toast("You just turned the ToggleButton off")
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun onSwitchCompatClicked(view: View) {
        val isOn = (view as SwitchCompat).isChecked

        if (isOn)
            toast("You just switched ON the Switch")
        else
            toast("You just switched OFF the Switch")
    }

    fun onCheckBoxClicked(view: View) {
        val isChecked = (view as CheckBox).isChecked

        if (isChecked)
            toast("You want some ${getString(R.string.checkbox)}")
        else
            toast("You DON'T want some ${getString(R.string.checkbox)}")
    }
}