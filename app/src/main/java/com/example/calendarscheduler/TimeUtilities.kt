package com.example.calendarscheduler

import android.content.res.Resources
import java.time.LocalDateTime

class TimeUtilities {
    companion object {
        private fun getMinutesSinceStartOfDay(): Int {
            return LocalDateTime.now().minute + LocalDateTime.now().hour * 60
        }

        fun toYValue(): Float {
            return ((Resources.getSystem().displayMetrics.heightPixels / 288) * (getMinutesSinceStartOfDay() / 5)).toFloat()
        }
    }
}