package ru.gusev.usdcursapplication.data.converters

import ru.gusev.usdcursapplication.data.model.CursInfoModel
import ru.gusev.usdcursapplication.data.remote.dto.CursRecordDto
import ru.gusev.usdcursapplication.data.remote.dto.ValuteDto
import ru.gusev.usdcursapplication.utils.TimeHelper
import java.util.*

private const val CURS_USD_NAME = "USD"

fun ValuteDto.toModel(date: Calendar): CursInfoModel = CursInfoModel(
    date = date,
    value = value?.replace(",", ".")?.toFloatOrNull() ?: 0f
)

fun CursRecordDto.toCursInfo(): CursInfoModel?  {
    TimeHelper.convertServerTimeToCalendar(date)?.let { parsedDate ->
        value?.replace(",", ".")?.toFloatOrNull()?.let { cursValue ->
            return CursInfoModel(parsedDate, cursValue)
        }
    }
    return null
}

fun List<ValuteDto>?.findUsdCurs(): ValuteDto?  = this?.firstOrNull {
        it.charCode == CURS_USD_NAME
    }

fun List<ValuteDto>?.getValueUsdCurs(): Float?  = findUsdCurs()?.value?.replace(",", ".")?.toFloatOrNull()
