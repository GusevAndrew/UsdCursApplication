package ru.gusev.usdcursapplication.ui.main

import androidx.annotation.IdRes
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

interface MainView: MvpView {
    @OneExecution
    fun showCursFragment()
    @OneExecution
    fun showSettingsFragment()
}

abstract class MainPresenter: MvpPresenter<MainView>() {

    abstract fun onClickByBottomBarItem(@IdRes idItem: Int)
}