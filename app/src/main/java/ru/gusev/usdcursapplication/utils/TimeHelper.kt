package ru.gusev.usdcursapplication.utils

import ru.gusev.usdcursapplication.utils.TimeHelper.DD_MM_YYYY_FORMAT
import ru.gusev.usdcursapplication.utils.TimeHelper.DD_MM_YYYY_FORMAT_WITH_SLASH
import ru.gusev.usdcursapplication.utils.TimeHelper.HH_MM_FORMAT
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TimeHelper {

    const val DD_MM_YYYY_FORMAT = "dd.MM.yyyy"
    const val DD_MM_YYYY_FORMAT_WITH_SLASH = "dd/MM/yyyy"
    const val HH_MM_FORMAT = "HH:mm"

    fun convertServerTimeToCalendar(serverTime: String?): Calendar? {
        if(serverTime.isNullOrEmpty()) {
            return null
        }
        val sdf = DD_MM_YYYY_FORMAT.toSimpleDateFormat()

        return try {
            sdf.parse(serverTime)?.let { date ->
                val calendar = Calendar.getInstance()
                calendar.time = date
                return calendar
            }
        } catch (e: ParseException) {
            return null
        }
    }

}

fun Calendar.getDateInServerFormat(): String  = getStringWithFormat(DD_MM_YYYY_FORMAT)

fun Calendar.getDateWithServerRequestFormat(): String = getStringWithFormat(DD_MM_YYYY_FORMAT_WITH_SLASH)

fun Calendar.getTimeFormat(): String = getStringWithFormat(HH_MM_FORMAT)

private fun Calendar.getStringWithFormat(format: String): String {
    val sdf = format.toSimpleDateFormat()
    return sdf.format(this.time)
}

private fun String.toSimpleDateFormat(): SimpleDateFormat = SimpleDateFormat(this, Locale.getDefault())