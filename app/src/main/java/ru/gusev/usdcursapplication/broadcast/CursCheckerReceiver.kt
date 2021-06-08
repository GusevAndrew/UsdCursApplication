package ru.gusev.usdcursapplication.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.gusev.usdcursapplication.UsdCursApplication
import javax.inject.Inject

class CursCheckerReceiver : BroadcastReceiver() {
    @Inject
    lateinit var presenter: CursCheckerPresenter

    init {
        UsdCursApplication.appComponent.inject(this)
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        presenter.onReceive()
    }

}