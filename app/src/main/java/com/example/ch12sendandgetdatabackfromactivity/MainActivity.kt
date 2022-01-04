package com.example.ch12sendandgetdatabackfromactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {
    val SECOND_ACTIVITY = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val obj_input_weight = findViewById<EditText>(R.id.input_weight)
        val obj_input_height = findViewById<EditText>(R.id.input_height)
        val obj_btn_send_data = findViewById<Button>(R.id.btn_send_data)
        val obj_txt_bmi = findViewById<TextView>(R.id.txt_bmi)

        obj_input_weight.setHint("weight (lbs)")
        obj_input_height.setHint("height (inches)")
        obj_txt_bmi.setText("")

        val resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val bmi = data!!.getFloatExtra("second_activity_data", 1.0F)
                val bmiString = "%.2f".format(bmi)
                obj_txt_bmi.setText("BMI: $bmiString ${getBMIDescription(bmi)}")
            }
        }

        obj_btn_send_data.setOnClickListener {
            val m_intent = Intent(this@MainActivity, SecondActivity::class.java)
            val m_bundle = Bundle()

            m_bundle.putFloat("weight", obj_input_weight.text.toString().toFloat())
            m_bundle.putFloat("height", obj_input_height.text.toString().toFloat())
            m_intent.putExtra("main_activity_data", m_bundle)

            //startActivityForResult(m_intent, SECOND_ACTIVITY)
            resultLauncher.launch(m_intent)
        }
    }

    override fun onResume() {
        super.onResume()
        clearInputs()
    }
    private fun getBMIDescription(bmi: Float) : String {
        return when (bmi) {
            in 1.0..18.5 -> "Underweight"
            in 18.6..24.9 -> "Normal weight"
            in 25.0..29.9 -> "Overweight"
            else -> "Obese"
        }
    }
    private fun clearInputs() {
        findViewById<EditText>(R.id.input_weight).setText("")
        findViewById<EditText>(R.id.input_height).setText("")
    }
}
