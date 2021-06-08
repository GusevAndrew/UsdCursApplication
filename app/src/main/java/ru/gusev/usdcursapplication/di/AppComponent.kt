package ru.gusev.usdcursapplication.di

import dagger.Component
import ru.gusev.usdcursapplication.broadcast.CursCheckerReceiver
import ru.gusev.usdcursapplication.broadcast.reboot_receiver.BootReceiver
import ru.gusev.usdcursapplication.di.module.AppModule
import ru.gusev.usdcursapplication.di.module.DomainModule
import ru.gusev.usdcursapplication.di.module.PresenterModule
import ru.gusev.usdcursapplication.ui.curs_list.CursListFragment
import ru.gusev.usdcursapplication.ui.main.MainActivity
import ru.gusev.usdcursapplication.ui.settings.SettingsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [PresenterModule::class, AppModule::class, DomainModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(cursListFragment: CursListFragment)
    fun inject(settingsFragment: SettingsFragment)

    fun inject(broadcastReceiver: CursCheckerReceiver)
    fun inject(bootReceiver: BootReceiver)
}