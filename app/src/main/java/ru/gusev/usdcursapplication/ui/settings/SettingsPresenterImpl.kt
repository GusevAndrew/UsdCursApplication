package ru.gusev.usdcursapplication.ui.settings

import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.gusev.usdcursapplication.broadcast.AlarmManagerHelper
import ru.gusev.usdcursapplication.data.exceptions.TimeNotificationNotSetException
import ru.gusev.usdcursapplication.domain.SettingsUseCase
import java.util.*
import java.util.concurrent.TimeUnit

class SettingsPresenterImpl(
    private val useCase: SettingsUseCase,
    private val alarmHelper: AlarmManagerHelper
) : SettingsPresenter() {

    companion object {
        private const val DELAY_FOR_SAVE_CURS_VALUE = 300L
    }

    private var lastTime: Calendar? = null
    private var isDataSet = false
    private var isNotificationEnabled = false
    private var isNotificationUpdating = false

    private var getDataDisposable: Disposable? = null
    private var updateCursValueDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getSettings()
    }

    override fun detachView(view: SettingsView?) {
        if (!isDataSet) {
            viewState.contentLoading(false)
            viewState.showErrorLoadData()
        }
        super.detachView(view)
        getDataDisposable?.dispose()
        getDataDisposable = null
    }

    override fun getSettings() {
        useCase
            .getCurrentSettings()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.contentLoading(true)
            }
            .doFinally {
                viewState.contentLoading(false)
            }
            .subscribe({
                lastTime = it.timeNotification
                isNotificationEnabled = it.isNotificationEnabled
                viewState.setData(it.timeNotification, it.cursValue, it.isNotificationEnabled)
                isDataSet = true
            }, {
                viewState.showErrorLoadData()
            }).also {
                getDataDisposable = it
            }
    }

    override fun saveTimeNotification(time: Calendar) {
        useCase
            .updateTimeNotification(time)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    //Nothing
                }

                override fun onComplete() {
                    lastTime = time
                    if (isNotificationEnabled) {
                        alarmHelper.activateAlarmManager(time)
                    }
                }

                override fun onError(e: Throwable) {
                    //Nothing
                }
            })
    }

    override fun saveCursValue(newCurs: String) {
        if (!isDataSet) {
            return
        }
        updateCursValueDisposable?.dispose()
        updateCursValueDisposable = null
        Single
            .timer(DELAY_FOR_SAVE_CURS_VALUE, TimeUnit.MILLISECONDS)
            .flatMapCompletable {
                useCase.updateCursValue(newCurs.toFloatOrNull())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //Nothing
            }, {
                //Nothing
            })
            .also {
                updateCursValueDisposable = it
            }

    }

    override fun onClickByTimeNotification() {
        viewState.showTimePickerDialog(lastTime ?: Calendar.getInstance())
    }

    override fun onClickByNotificationEnabled() {
        if (isNotificationUpdating) {
            return
        }
        isNotificationUpdating = true
        useCase
            .enabledNotification(!isNotificationEnabled)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                isNotificationUpdating = false
            }
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    //Nothing
                }

                override fun onComplete() {
                    isNotificationEnabled = !isNotificationEnabled
                    viewState.enabledNotification(isNotificationEnabled)
                    if (isNotificationEnabled) {
                        lastTime?.let {
                            alarmHelper.activateAlarmManager(it)
                        }
                    } else {
                        alarmHelper.disableAlarmManager()
                    }
                }

                override fun onError(e: Throwable) {
                    if (e is TimeNotificationNotSetException) {
                        viewState.showErrorNotificationEnabledTimeNotSet()
                    } else {
                        viewState.showErrorNotificationEnabled()
                    }
                }
            })
    }
}