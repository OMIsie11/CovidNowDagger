package io.github.omisie11.coronatrackerplayground.di

import dagger.Module
import dagger.Provides
import io.github.omisie11.coronatrackerplayground.data.remote.ApiService
import io.github.omisie11.coronatrackerplayground.data.remote.BASE_URL
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiService(): ApiService = retrofit().create()
}
