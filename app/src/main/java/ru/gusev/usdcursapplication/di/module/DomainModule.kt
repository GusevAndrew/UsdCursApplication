package ru.gusev.usdcursapplication.di.module

import dagger.Module
import dagger.Provides
import ru.gusev.usdcursapplication.data.repository.CursRepository
import ru.gusev.usdcursapplication.data.repository.SettingsRepository
import ru.gusev.usdcursapplication.domain.*

@Module
class DomainModule {

    @Provides
    fun provideCursListUseCase(cursRepository: CursRepository): CursListUseCase {
        return CursListUseCaseImpl(cursRepository)
    }

    @Provides
    fun provideSettingsUseCase(settingsRepository: SettingsRepository): SettingsUseCase {
        return SettingsUseCaseImpl(settingsRepository)
    }

    @Provides
    fun provideCursCheckerUseCase(cursRepository: CursRepository, settingsRepository: SettingsRepository): CursCheckerUseCase {
        return CursCheckerUseCaseImpl(cursRepository, settingsRepository)
    }
}