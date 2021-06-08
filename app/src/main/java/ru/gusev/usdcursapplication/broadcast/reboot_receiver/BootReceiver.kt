package ru.gusev.usdcursapplication.broadcast.reboot_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.gusev.usdcursapplication.UsdCursApplication
import javax.inject.Inject

class BootReceiver: BroadcastReceiver() {

    @Inject
    lateinit var presenter: BootPresenter

    init {
        UsdCursApplication.appComponent.inject(this)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            presenter.startAlarmManagerIfNeed()
        }
    }
}