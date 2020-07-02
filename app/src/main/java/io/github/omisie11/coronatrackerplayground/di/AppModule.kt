package io.github.omisie11.coronatrackerplayground.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import io.github.omisie11.coronatrackerplayground.MainApplication

@Module
abstract class AppModule {

    @Binds
    abstract fun bindApplication(app: MainApplication): Application

    @Binds
    abstract fun bindContext(app: MainApplication): Context
}
