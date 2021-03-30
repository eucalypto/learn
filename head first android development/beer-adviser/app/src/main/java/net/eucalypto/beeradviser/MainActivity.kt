package net.eucalypto.beeradviser

import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private val expert = BeerExpert()

    fun onClickFindBeer(view: View) {
        val brandsView: TextView = findViewById(R.id.brands)
        val color: Spinner = findViewById(R.id.color)
        val beerType = color.selectedItem.toString()
        brandsView.text = expert.getBrands(beerType).joinToString("\n")
    }
}