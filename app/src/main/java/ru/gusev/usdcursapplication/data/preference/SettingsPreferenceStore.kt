package ru.gusev.usdcursapplication.data.preference

import android.content.SharedPreferences
import ru.gusev.usdcursapplication.data.exceptions.TimeNotificationNotSetException
import java.util.*
import javax.inject.Inject

class SettingsPreferenceStore @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val PREF_NOTIFICATION_TIME = "pref_notification_time"
        private const val PREF_CURS_VALUE = "pref_curs_value"
        private const val PREF_NOTIFICATION_ENABLED_FLAG = "pref_notification_enabled_flag"
    }

    var timeNotification: Calendar?
        get() {
            val timeLong = sharedPreferences.getLong(PREF_NOTIFICATION_TIME, -1)
            if (timeLong >= 0) {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = timeLong
                return calendar
            }
            return null
        }
        set(value) {
            with(sharedPreferences.edit()) {
                if (value == null) {
                    remove(PREF_NOTIFICATION_TIME)
                } else {
                    putLong(PREF_NOTIFICATION_TIME, value.timeInMillis)
                }
                apply()
            }
        }

    var cursValue: Float?
        get() {
            val cursValue = sharedPreferences.getFloat(PREF_CURS_VALUE, Float.NaN)
            return if(cursValue.isNaN()) {
                null
            } else {
                cursValue
            }
        }
        set(value) {
            with(sharedPreferences.edit()) {
                if (value == null) {
                    remove(PREF_CURS_VALUE)
                } else {
                    putFloat(PREF_CURS_VALUE, value)
                }
                apply()
            }
        }

    var isNotificationEnabled: Boolean
        get() {
            return sharedPreferences.getBoolean(PREF_NOTIFICATION_ENABLED_FLAG, false)
        }
        set(value) {
            with(sharedPreferences.edit()) {
                if (value) {
                    if(timeNotification == null) {
                        putBoolean(PREF_NOTIFICATION_ENABLED_FLAG, false)
                        apply()
                        throw TimeNotificationNotSetException()
                    }
                }
                putBoolean(PREF_NOTIFICATION_ENABLED_FLAG, value)
                apply()
            }
        }
}