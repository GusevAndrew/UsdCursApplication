package ru.gusev.usdcursapplication

import android.app.Application
import ru.gusev.usdcursapplication.di.AppComponent
import ru.gusev.usdcursapplication.di.DaggerAppComponent
import ru.gusev.usdcursapplication.di.module.AppModule

class UsdCursApplication: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        createAppComponent()
    }

    private fun createAppComponent() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}