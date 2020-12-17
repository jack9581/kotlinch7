 package com.example.kotlinch7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var rabprogress = 0
    private var torprogress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val scope = CoroutineScope(Dispatchers.Default)
        btn_start.setOnClickListener {
            rabprogress = 0
            torprogress = 0
            seekBar.progress = 0
            seekBar2.progress = 0
            scope.launch {
                run1()
            }

        }
    }
    private suspend fun run1(){
        while (rabprogress < 100 && torprogress < 100)
            try {
                delay(100)
                withContext(Dispatchers.Main){
                    rabprogress += (Math.random() * 4).toInt()
                    torprogress += (Math.random() * 3).toInt()
                }
                withContext(Dispatchers.Main){
                    seekBar.progress = rabprogress
                    seekBar2.progress = torprogress
                }

                withContext(Dispatchers.Main){
                    if (rabprogress >= 100 && torprogress < 100) {
                        Toast.makeText(this@MainActivity,"兔子勝利", Toast.LENGTH_SHORT).show()
                        btn_start.isEnabled = true
                    }
                    else if(torprogress >= 100 && rabprogress < 100) {
                        Toast.makeText(this@MainActivity, "烏龜勝利", Toast.LENGTH_SHORT).show()
                        btn_start.isEnabled = true
                    }
                }

            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
    }
