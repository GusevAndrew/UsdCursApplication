package ru.gusev.usdcursapplication.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.gusev.usdcursapplication.broadcast.AlarmManagerHelper
import ru.gusev.usdcursapplication.broadcast.CursCheckerPresenter
import ru.gusev.usdcursapplication.broadcast.CursCheckerPresenterImpl
import ru.gusev.usdcursapplication.broadcast.reboot_receiver.BootPresenter
import ru.gusev.usdcursapplication.broadcast.reboot_receiver.BootPresenterImpl
import ru.gusev.usdcursapplication.domain.CursCheckerUseCase
import ru.gusev.usdcursapplication.domain.CursListUseCase
import ru.gusev.usdcursapplication.domain.SettingsUseCase
import ru.gusev.usdcursapplication.notification.NotificationHelper
import ru.gusev.usdcursapplication.ui.curs_list.CursListPresenter
import ru.gusev.usdcursapplication.ui.curs_list.CursListPresenterImpl
import ru.gusev.usdcursapplication.ui.main.MainPresenter
import ru.gusev.usdcursapplication.ui.main.MainPresenterImpl
import ru.gusev.usdcursapplication.ui.settings.SettingsPresenter
import ru.gusev.usdcursapplication.ui.settings.SettingsPresenterImpl

@Module
class PresenterModule {

    @Provides
    fun provideMainPresenter(): MainPresenter {
        return MainPresenterImpl()
    }

    @Provides
    fun provideCursListPresenter(cursListUseCase: CursListUseCase): CursListPresenter {
        return CursListPresenterImpl(cursListUseCase)
    }

    @Provides
    fun provideSettingsPresenter(alarmHelper: AlarmManagerHelper, settingsUseCase: SettingsUseCase): SettingsPresenter {
        return SettingsPresenterImpl(settingsUseCase, alarmHelper)
    }

    @Provides
    fun provideCursCheckerPresenter(alarmHelper: AlarmManagerHelper, context: Context, notificationHelper: NotificationHelper, cursCheckerUseCase: CursCheckerUseCase): CursCheckerPresenter {
        return CursCheckerPresenterImpl(context, alarmHelper, notificationHelper, cursCheckerUseCase)
    }

    @Provides
    fun provideBootPresenter(alarmHelper: AlarmManagerHelper, settingsUseCase: SettingsUseCase): BootPresenter {
        return BootPresenterImpl(settingsUseCase, alarmHelper)
    }
}