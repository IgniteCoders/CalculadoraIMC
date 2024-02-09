package com.example.calculadoraimc.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.calculadoraimc.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.Slider
import java.text.DecimalFormat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var weightTextView:TextView
    private lateinit var addWeightFab:FloatingActionButton
    private lateinit var subWeightFab:FloatingActionButton
    private lateinit var heightTextView:TextView
    private lateinit var heightSlider:Slider
    private lateinit var calculateButton:Button
    private lateinit var resultTextView:TextView
    private lateinit var descriptionTextView:TextView

    private var currentWeight:Int = 60
    private var currentHeight:Int = 160

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    fun initView() {
        weightTextView = findViewById(R.id.weightTextView)
        heightTextView = findViewById(R.id.heightTextView)
        addWeightFab = findViewById(R.id.addWeightFab)
        subWeightFab = findViewById(R.id.subWeightFab)
        heightSlider = findViewById(R.id.heightSlider)
        calculateButton = findViewById(R.id.calculateButton)
        resultTextView = findViewById(R.id.resultTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)

        setHeightText ()
        setWeightText ()

        heightSlider.value = currentHeight.toFloat()

        heightSlider.addOnChangeListener { _, value, _ ->
            currentHeight = value.toInt()
            setHeightText()
        }

        addWeightFab.setOnClickListener {
            currentWeight ++
            setWeightText ()
        }

        subWeightFab.setOnClickListener {
            currentWeight --
            setWeightText ()
        }

        calculateButton.setOnClickListener {
            calculateBMI()
        }
    }

    private fun setHeightText() {
        heightTextView.text = getString(R.string.height_text, currentHeight.toString())
    }

    private fun setWeightText () {
        weightTextView.text = getString(R.string.weight_text, currentWeight.toString())
    }

    private fun calculateBMI() {
        val result = currentWeight / (currentHeight.toFloat() / 100).pow(2)
        val formatter:DecimalFormat = DecimalFormat("#.##")
        resultTextView.text = formatter.format(result)

        val descriptionResult:String?
        val descriptionColor:Int?
        when(result) {
            in 0.0..18.5 -> {
                descriptionResult = getString(R.string.under_weight)
                descriptionColor = getColor(R.color.under_weight_color)
            }
            in 18.5..24.9 -> {
                descriptionResult = getString(R.string.normal_weight)
                descriptionColor = getColor(R.color.normal_weight_color)
            }
            in 24.9..29.9 -> {
                descriptionResult = getString(R.string.over_weight)
                descriptionColor = getColor(R.color.over_weight_color)
            }
            else -> {
                descriptionResult = getString(R.string.obesity)
                descriptionColor = getColor(R.color.obesity_color)
            }
        }
        descriptionTextView.text = descriptionResult
        descriptionTextView.setTextColor(descriptionColor)
        resultTextView.setTextColor(descriptionColor)
        //Log.i("IMC", "El resultado de la formula es: ${formatter.format(result)}")
    }
}