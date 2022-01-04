package com.example.ch12sendandgetdatabackfromactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val bundle = intent.getBundleExtra("main_activity_data")

        val height = bundle!!.getFloat("height")
        val weight = bundle.getFloat("weight")

        val obj_txt_intentdata = findViewById<TextView>(R.id.txt_intentdata)
        obj_txt_intentdata.text = "Height: $height" + System.lineSeparator() + "Weight: $weight"
        val obj_btn_calculate = findViewById<Button>(R.id.btn_calculate)
        obj_btn_calculate.setOnClickListener {
            val m_intent = Intent()
            val m_bmi = 703 * (weight / (height * height))

            m_intent.putExtra("second_activity_data", m_bmi)
            setResult(Activity.RESULT_OK, m_intent)
            finish()
        }
    }
}