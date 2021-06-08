package ru.gusev.usdcursapplication.data.repository

import io.reactivex.Single
import ru.gusev.usdcursapplication.data.remote.RestService
import ru.gusev.usdcursapplication.data.remote.dto.DynamicCursDto
import ru.gusev.usdcursapplication.data.remote.dto.ValCursDto
import ru.gusev.usdcursapplication.utils.checkResponse
import ru.gusev.usdcursapplication.utils.getDateWithServerRequestFormat
import java.util.*
import javax.inject.Inject

class CursRepository @Inject constructor(private var restService: RestService) {

    fun getCurrentCurs(): Single<ValCursDto> {
        return restService
            .getCurrentCurs()
            .map {
                it.checkResponse()
                return@map it.body()
            }
    }

    fun getDynamicCursByRange(dateFrom: Calendar, dateTo: Calendar): Single<DynamicCursDto> {
        return restService
            .getCursUsdByRange(dateFrom.getDateWithServerRequestFormat(), dateTo.getDateWithServerRequestFormat())
            .map {
                it.checkResponse()
                return@map it.body()
            }
    }
}