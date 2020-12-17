package com.example.kotlinch7_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val scope = CoroutineScope(Dispatchers.Default)
       btn_calculate.setOnClickListener {
            when{

                ed_height.length()<1 -> Toast.makeText(this,"請輸入身高",
                        Toast.LENGTH_SHORT).show()
                ed_weight.length()<1 -> Toast.makeText(this,"請輸入體重",
                        Toast.LENGTH_SHORT).show()
                else-> {
                    tv_weight.text = "標準體重\n無"
                    tv_bmi.text = "體脂肪\n無"
                    ll_progress.visibility = View.VISIBLE
                    progressBar2.progress = 0
                    tv_progress.text = "0%"
                    scope.launch {
                        run1()
                    }

                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(it.windowToken,0)
                }
            }
        }
    }
    private suspend fun run1(){
        var progress = 0
        val cal_height = ed_height.text.toString().toDouble()
        val cal_weight = ed_weight.text.toString().toDouble()
        val cal_standweight: Double
        val cal_bodyfat: Double
        while (progress < 100)
            try {
                delay(50)
                withContext(Dispatchers.Main){
                    progress++
                }
                withContext(Dispatchers.Main){
                    progressBar.progress=progress
                    progressBar2.progress=progress
                    tv_progress.text= String.format("%d%%",progress)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            ll_progress.visibility = View.GONE

            if (btn_boy.isChecked) {
                cal_standweight = (cal_height - 80) * 0.7
                cal_bodyfat = (cal_weight - 0.88 * cal_standweight) / cal_weight * 100
            } else {
                cal_standweight = (cal_height - 70) * 0.6
                cal_bodyfat = (cal_weight - 0.82 * cal_standweight) / cal_weight * 100
            }
            tv_weight.text = String.format("標準體重 \n%.2f", cal_standweight)
            tv_bmi.text = String.format("體脂肪 \n%.2f", cal_bodyfat)
        }

    }

