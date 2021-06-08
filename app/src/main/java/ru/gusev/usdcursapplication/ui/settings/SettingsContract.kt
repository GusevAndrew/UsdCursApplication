package ru.gusev.usdcursapplication.ui.settings

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution
import java.util.*

interface SettingsView: MvpView {
    @OneExecution
    fun contentLoading(isLoading: Boolean)
    @OneExecution
    fun setData(time: Calendar?, cursValue: Float?, isNotificationEnabled: Boolean)
    @OneExecution
    fun showErrorLoadData()
    @OneExecution
    fun showTimePickerDialog(calendar: Calendar)

    @OneExecution
    fun enabledNotification(isEnabled: Boolean)
    @OneExecution
    fun showErrorNotificationEnabled()
    @OneExecution
    fun showErrorNotificationEnabledTimeNotSet()
}

abstract class SettingsPresenter: MvpPresenter<SettingsView>() {
    abstract fun getSettings()

    abstract fun saveTimeNotification(time: Calendar)
    abstract fun saveCursValue(newCurs: String)

    abstract fun onClickByTimeNotification()
    abstract fun onClickByNotificationEnabled()
}