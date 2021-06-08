package ru.gusev.usdcursapplication.domain

import io.reactivex.Single
import ru.gusev.usdcursapplication.data.converters.getValueUsdCurs
import ru.gusev.usdcursapplication.data.exceptions.NotificationDisableException
import ru.gusev.usdcursapplication.data.model.SettingsAppModel
import ru.gusev.usdcursapplication.data.model.TypeNotification
import ru.gusev.usdcursapplication.data.repository.CursRepository
import ru.gusev.usdcursapplication.data.repository.SettingsRepository

interface CursCheckerUseCase {
    fun checkTypeNotification(): Single<Pair<TypeNotification, Float>>
    fun getCurrentSettings(): Single<SettingsAppModel>
}

class CursCheckerUseCaseImpl(private val cursRepository: CursRepository, private val settingsRepository: SettingsRepository): CursCheckerUseCase {
    override fun checkTypeNotification(): Single<Pair<TypeNotification, Float>> {
        return settingsRepository
            .getCurrentSettings()
            .flatMap { currentSettings ->
                if(!currentSettings.isNotificationEnabled) {
                    throw NotificationDisableException()
                }
                return@flatMap cursRepository
                    .getCurrentCurs().map { valCursDto ->
                        val curs = valCursDto.valutesDto.getValueUsdCurs() ?: 0f
                        val userCurs = currentSettings.cursValue ?: 0f
                        if(userCurs > 0f) {
                            if(userCurs < curs) {
                                return@map Pair(TypeNotification.CURS_BIGGEST, userCurs)
                            }
                        } else {
                            return@map Pair(TypeNotification.CURS_UPDATED, 0f)
                        }
                        return@map Pair(TypeNotification.NONE, 0f)
                    }
            }
    }

    override fun getCurrentSettings(): Single<SettingsAppModel> {
        return settingsRepository.getCurrentSettings()
    }

}