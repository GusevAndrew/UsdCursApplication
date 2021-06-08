package ru.gusev.usdcursapplication.broadcast

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*
import javax.inject.Inject

class AlarmManagerHelper @Inject constructor(
    private val alarmManager: AlarmManager,
    private val context: Context
) {
    fun activateAlarmManager(triggerTime: Calendar) {
        disableAlarmManager()
        (context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager)?.let {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, triggerTime.get(Calendar.HOUR_OF_DAY))
            calendar.set(Calendar.MINUTE, triggerTime.get(Calendar.MINUTE))
            calendar.set(Calendar.SECOND, 0)
            if (calendar.timeInMillis < Calendar.getInstance().timeInMillis) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, getIntent())
            alarmManager.nextAlarmClock?.let {
                val calendarTwo = Calendar.getInstance()
                calendarTwo.timeInMillis = it.triggerTime
                alarmManager.cancel(it.showIntent)
            }
        }
    }

    fun disableAlarmManager() {
        alarmManager.cancel(getIntent())
    }

    private fun getIntent(): PendingIntent {
        val intent = Intent(context, CursCheckerReceiver::class.java)
        return PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}