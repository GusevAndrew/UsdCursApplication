package ru.gusev.usdcursapplication.di.module

import android.app.AlarmManager
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.gusev.usdcursapplication.UsdCursApplication
import ru.gusev.usdcursapplication.data.remote.RestService
import javax.inject.Singleton

@Module
class AppModule(private val usdCursApplication: UsdCursApplication) {

    companion object {
        const val SHARED_PREF_NAME = "app_shared_preferences"
    }

    @Provides
    fun provideContext(): Context {
        return usdCursApplication
    }

    @Provides
    fun provideAlarmManager(): AlarmManager {
        return usdCursApplication.getSystemService(ALARM_SERVICE) as AlarmManager
    }

    @Provides
    @Singleton
    fun provideRestService(): RestService {
        return RestService.createRestService()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        return usdCursApplication.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
}