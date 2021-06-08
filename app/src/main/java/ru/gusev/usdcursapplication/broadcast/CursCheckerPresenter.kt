package ru.gusev.usdcursapplication.broadcast

import android.content.Context
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.gusev.usdcursapplication.R
import ru.gusev.usdcursapplication.data.model.SettingsAppModel
import ru.gusev.usdcursapplication.data.model.TypeNotification
import ru.gusev.usdcursapplication.domain.CursCheckerUseCase
import ru.gusev.usdcursapplication.notification.NotificationHelper

abstract class CursCheckerPresenter {
    abstract fun onReceive()
}

class CursCheckerPresenterImpl(
    private val context: Context,
    private val alarmHelper: AlarmManagerHelper,
    private val notificationHelper: NotificationHelper,
    private val useCase: CursCheckerUseCase
) : CursCheckerPresenter() {
    override fun onReceive() {
        setNewAlarm()
        useCase
            .checkTypeNotification()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Pair<TypeNotification, Float>> {
                override fun onSubscribe(d: Disposable) {
                    //Nothing
                }

                override fun onSuccess(t: Pair<TypeNotification, Float>) {
                    val message =
                        when (t.first) {
                            TypeNotification.CURS_BIGGEST -> context.getString(
                                R.string.notification_curs_biggest,
                                t.second
                            )
                            TypeNotification.CURS_UPDATED -> context.getString(R.string.notification_curs_updated)
                            else -> ""
                        }
                    if (message.isNotEmpty()) {
                        notificationHelper.sendNotification(message)
                    }
                }

                override fun onError(e: Throwable) {
                    //Nothing
                }

            })
    }

    private fun setNewAlarm() {
        useCase
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