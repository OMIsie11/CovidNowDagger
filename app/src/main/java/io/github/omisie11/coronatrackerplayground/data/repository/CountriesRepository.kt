package io.github.omisie11.coronatrackerplayground.data.repository

import android.content.SharedPreferences
import io.github.omisie11.coronatrackerplayground.data.local.dao.CountriesDao
import io.github.omisie11.coronatrackerplayground.data.local.model.Country
import io.github.omisie11.coronatrackerplayground.data.mappers.mapToLocalCountryList
import io.github.omisie11.coronatrackerplayground.data.remote.ApiService
import io.github.omisie11.coronatrackerplayground.data.remote.model.CountriesRemote
import io.github.omisie11.coronatrackerplayground.util.PREFS_LAST_REFRESH_COUNTRIES
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

@Singleton
class CountriesRepository @Inject constructor(
    private val apiService: ApiService,
    private val countriesDao: CountriesDao,
    sharedPrefs: SharedPreferences
) : BaseRepository<CountriesRemote, List<Country>>(sharedPrefs) {

    override val lastRefreshKey: String = PREFS_LAST_REFRESH_COUNTRIES

    fun getCountriesNamesFlow(): Flow<List<String>> = countriesDao.getCountriesNamesFlow()

    override suspend fun makeApiCall(): Response<CountriesRemote> = apiService.getCountries()

    override suspend fun mapRemoteModelToLocal(data: CountriesRemote): List<Country> =
        data.mapToLocalCountryList()

    override suspend fun saveToDb(data: List<Country>) {
        countriesDao.replace(data)
    }
}
