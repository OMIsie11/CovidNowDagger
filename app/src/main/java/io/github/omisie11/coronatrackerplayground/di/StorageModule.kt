package io.github.omisie11.coronatrackerplayground.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.github.omisie11.coronatrackerplayground.data.local.AppDatabase
import io.github.omisie11.coronatrackerplayground.data.local.dao.CountriesDao
import io.github.omisie11.coronatrackerplayground.data.local.dao.GlobalSummaryDao
import io.github.omisie11.coronatrackerplayground.data.local.dao.LocalSummaryDao
import javax.inject.Singleton

@Module
class StorageModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "corona_data.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideGlobalSummaryDao(db: AppDatabase): GlobalSummaryDao = db.globalSummaryDao()

    @Singleton
    @Provides
    fun provideLocalSummaryDao(db: AppDatabase): LocalSummaryDao = db.localSummaryDao()

    @Singleton
    @Provides
    fun provideCountriesDao(db: AppDatabase): CountriesDao = db.countriesDao()
}
