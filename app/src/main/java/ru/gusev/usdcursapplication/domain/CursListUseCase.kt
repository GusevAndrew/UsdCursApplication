package ru.gusev.usdcursapplication.domain

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.gusev.usdcursapplication.data.converters.findUsdCurs
import ru.gusev.usdcursapplication.data.converters.toCursInfo
import ru.gusev.usdcursapplication.data.converters.toModel
import ru.gusev.usdcursapplication.data.exceptions.FailGetDataFromServer
import ru.gusev.usdcursapplication.data.model.CursInfoModel
import ru.gusev.usdcursapplication.data.repository.CursRepository
import ru.gusev.usdcursapplication.ui.curs_list.DataForCursListFragment
import ru.gusev.usdcursapplication.utils.TimeHelper
import java.util.*

interface CursListUseCase {
    fun getCurrentUsdCurs(): Single<DataForCursListFragment>
}

class CursListUseCaseImpl(private val cursRepository: CursRepository) : CursListUseCase {

    override fun getCurrentUsdCurs(): Single<DataForCursListFragment> {
        return Single.zip(
            getCurrentCurs().subscribeOn(Schedulers.io()),
            getDynamicCurs().subscribeOn(Schedulers.io()),
            { currentCurs, dynamicCurs ->
                DataForCursListFragment(currentCurs, dynamicCurs.sortedByDescending {
                    it.date.timeInMillis
                })
            }
        )
    }

    private fun getCurrentCurs(): Single<CursInfoModel> {
        return cursRepository
            .getCurrentCurs()
            .map { cursDto ->
                TimeHelper.convertServerTimeToCalendar(cursDto.date)?.let { date ->
                    return@map cursDto.valutesDto.findUsdCurs()?.toModel(date) ?: CursInfoModel(date = date, 0f)
                }
                throw FailGetDataFromServer()
            }
    }

    private fun getDynamicCurs(): Single<List<CursInfoModel>> {
        val dateFrom = Calendar.getInstance()
        dateFrom.add(Calendar.DAY_OF_MONTH, -1)
        dateFrom.add(Calendar.MONTH, -1)
        val dateTo = Calendar.getInstance()
        dateTo.add(Calendar.DAY_OF_MONTH, -1)
        return cursRepository
            .getDynamicCursByRange(dateFrom, dateTo)
            .map { dynamicCurs ->
                return@map dynamicCurs.records?.let {
                    it.mapNotNull { cursRecord ->
                        cursRecord.toCursInfo()
                    }
                }?: listOf()
            }
    }

}