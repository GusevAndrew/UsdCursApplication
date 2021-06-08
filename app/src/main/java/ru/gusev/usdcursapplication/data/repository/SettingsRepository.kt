package ru.gusev.usdcursapplication.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.gusev.usdcursapplication.data.model.SettingsAppModel
import ru.gusev.usdcursapplication.data.preference.SettingsPreferenceStore
import java.util.*
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val preferenceStore: SettingsPreferenceStore) {

    fun getCurrentSettings(): Single<SettingsAppModel> {
        return Single.fromCallable {
            return@fromCallable SettingsAppModel(
                preferenceStore.timeNotification,
                preferenceStore.cursValue,
                preferenceStore.isNotificationEnabled
            )
        }
    }

    fun updateTimeNotification(timeNotification: Calendar): Completable {
        return Completable.fromCallable {
            preferenceStore.timeNotification = timeNotification
            return@fromCallable true
        }
    }

    fun updateCursValue(cursValue: Float?): Completable {
        return Completable.fromCallable {
            preferenceStore.cursValue = cursValue
            return@fromCallable true
        }
    }

    fun enableNotification(isEnabled: Boolean): Completable {
        return Completable.fromCallable {
            preferenceStore.isNotificationEnabled = isEnabled
            return@fromCallable true
        }
    }
}