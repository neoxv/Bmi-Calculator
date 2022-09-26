package com.example.bmi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weight = findViewById<EditText>(R.id.etWeight)
        val height = findViewById<EditText>(R.id.etHeight)
        val calculate = findViewById<Button>(R.id.btnCalculate)
        val reset = findViewById<Button>(R.id.btnReset)

        calculate.setOnClickListener {
            if(validateInputs(weight.text.toString(),height.text.toString())){

                val weightValue = weight.text.toString().toDouble()
                val heightValue = height.text.toString().toDouble()

                val bmi = weightValue/ (heightValue / 100).pow((2).toDouble())
                val bmiFinal = String.format("%.2f",bmi).toDouble()
                displayResult(bmiFinal)
            }
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }

        }

        reset.setOnClickListener {
            val textViews = listOf<Int>(R.id.tvBmiInfo, R.id.tvBmiResult, R.id.tvBmiValue)
            val editTexts = listOf<Int>(R.id.etWeight,R.id.etHeight)
            val views = mapOf<String,List<Int>>(
                "TextView" to textViews,
                "EditText" to editTexts
            )
            resetViews(views)
        }
    }

    private  fun validateInputs(weight:String, height:String):Boolean{
        Log.i("params",weight)
        Log.i("params",height)
       return  when{
           weight.isEmpty() -> {
               Toast.makeText(this, "Weight is empty", Toast.LENGTH_LONG).show()
               false
           }
           height.isEmpty() -> {
               Toast.makeText(this, "Height is empty", Toast.LENGTH_LONG).show()
               false
           }
           else -> {
               true
           }
       }
    }

    private  fun displayResult(bmi:Double){
        val bmiValue = findViewById<TextView>(R.id.tvBmiValue)
        val result = findViewById<TextView>(R.id.tvBmiResult)
        val info = findViewById<TextView>(R.id.tvBmiInfo)
        bmiValue.text = bmi.toString()
        val infoText = "Normal Range 18.5 - 24.9"
        info.text = infoText

        var resultColor = 0
        var resultText = ""

        when{
            bmi < 18.50 ->{
                resultText = "Underweight"
                resultColor = R.color.underweight
            }
            bmi in 18.50..24.99 ->{
                resultText = "Normal"
                resultColor = R.color.normal
            }
            bmi in 25.00..29.99 ->{
                resultText = "Overweight"
                resultColor = R.color.overweight
            }
            bmi > 29.99 ->{
                resultText = "Obese"
                resultColor = R.color.obese
            }
        }

        result.setTextColor(ContextCompat.getColor(this,resultColor))
        result.text = resultText
    }

    private fun resetViews(views: Map<String,List<Int>>){
        for((type,viewList) in views){
            if(type == "TextView"){
                for(textView in viewList ){
                    (findViewById<TextView>(textView)).text = ""
                }
            }else if(type == "EditText") {
                for (textView in viewList){
                    (findViewById<EditText>(textView)).text.clear()
                }
            }
        }
    }
}