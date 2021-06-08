package ru.gusev.usdcursapplication.domain

import io.reactivex.Completable
import io.reactivex.Single
import ru.gusev.usdcursapplication.data.model.SettingsAppModel
import ru.gusev.usdcursapplication.data.repository.SettingsRepository
import java.util.*

interface SettingsUseCase {
    fun getCurrentSettings(): Single<SettingsAppModel>

    fun updateTimeNotification(timeNotification: Calendar): Completable
    fun updateCursValue(cursValue: Float?): Completable
    fun enabledNotification(isEnabled: Boolean): Completable
}

class SettingsUseCaseImpl constructor(private val settingsRepository: SettingsRepository) :
    SettingsUseCase {
    override fun getCurrentSettings(): Single<SettingsAppModel> {
        return settingsRepository.getCurrentSettings()
    }

    override fun updateTimeNotification(timeNotification: Calendar): Completable {
        return settingsRepository.updateTimeNotification(timeNotification)
    }

    override fun updateCursValue(cursValue: Float?): Completable {
        return settingsRepository.updateCursValue(cursValue)
    }

    override fun enabledNotification(isEnabled: Boolean): Completable {
        return settingsRepository.enableNotification(isEnabled)
    }

}