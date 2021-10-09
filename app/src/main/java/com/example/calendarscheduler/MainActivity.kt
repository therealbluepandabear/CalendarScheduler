package com.example.calendarscheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.calendarscheduler.databinding.ActivityMainBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

const val format = "hh:mm a"
const val threadInterval = 60

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindings()

        update()
    }

    private fun update() {
        val t: Thread = object : Thread() {
            override fun run() {
                try {
                    while (!isInterrupted) {
                        sleep(threadInterval.toLong())
                        runOnUiThread {
                            val instance: LocalTime = LocalTime.now()
                            val yVal = TimeUtilities.toYValue()

                            binding.timeView.y = yVal
                            binding.timeTextView.y = yVal + 10

                            binding.timeTextView.text = DateTimeFormatter.ofPattern(format).format(instance)
                        }
                    }
                } catch (e: InterruptedException) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        t.start()
    }

    private fun setBindings() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
