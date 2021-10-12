package com.example.calendarscheduler

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calendarscheduler.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
        addHourlyRectangles()
    }

    private fun addHourlyRectangles() {
        for (i in 0..23) {
            val view = LinearLayout(this)
            view.layoutParams = LinearLayout.LayoutParams(Resources.getSystem().displayMetrics.widthPixels, (Resources.getSystem().displayMetrics.heightPixels / 24), 1.0f)

            val txt = TextView(this)

            txt.text = "$i a.m."

            if (i == 0) {
                txt.text = "12 a.m."
            }

            if (i > 12) {
                txt.text = "${(i - 12)} p.m."
            }

            view.addView(txt)

            if (i % 2 == 0) view.setBackgroundColor(Color.WHITE) else view.setBackgroundColor(Color.LTGRAY)

            binding.rectangleLayout.addView(view)
        }
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
