package ru.gusev.usdcursapplication.ui.main

import ru.gusev.usdcursapplication.R

class MainPresenterImpl: MainPresenter() {
    override fun onClickByBottomBarItem(idItem: Int) {
        when(idItem) {
            R.id.item_curs -> viewState.showCursFragment()
            R.id.item_settings -> viewState.showSettingsFragment()
        }
    }
}