package ru.gusev.usdcursapplication.broadcast.reboot_receiver

import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.gusev.usdcursapplication.broadcast.AlarmManagerHelper
import ru.gusev.usdcursapplication.data.model.SettingsAppModel
import ru.gusev.usdcursapplication.domain.SettingsUseCase

interface BootPresenter {
    fun startAlarmManagerIfNeed()
}


class BootPresenterImpl(
    private val settingsUseCase: SettingsUseCase,
    private val alarmHelper: AlarmManagerHelper
    ): BootPresenter {
    override fun startAlarmManagerIfNeed() {
        settingsUseCase
            .getCurrentSettings()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<SettingsAppModel> {
                override fun onSubscribe(d: Disposable) {
                    //Nothing
                }

                override fun onSuccess(currentSettings: SettingsAppModel) {
                    if(currentSettings.isNotificationEnabled) {
                        currentSettings.timeNotification?.let {
                            alarmHelper.activateAlarmManager(it)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    //Nothing
                }

            })
    }

}