package ru.gusev.usdcursapplication.ui.curs_list

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

interface CursListView: MvpView {

    @OneExecution
    fun showLoadingContent(isLoading: Boolean, isFullScreen: Boolean)
    @OneExecution
    fun setData(dataForScreen: DataForCursListFragment)
    @OneExecution
    fun showErrorGetCursList(isFullScreen: Boolean)
}

abstract class CursListPresenter: MvpPresenter<CursListView>() {
    abstract fun getCursInfo(isSwipeRefresh: Boolean)
}