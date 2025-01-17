package io.github.omisie11.coronatrackerplayground.data.repository

import android.content.SharedPreferences
import com.github.mikephil.charting.data.PieEntry
import io.github.omisie11.coronatrackerplayground.data.local.dao.GlobalSummaryDao
import io.github.omisie11.coronatrackerplayground.data.local.model.GlobalSummary
import io.github.omisie11.coronatrackerplayground.data.mappers.mapToLocalSummary
import io.github.omisie11.coronatrackerplayground.data.remote.ApiService
import io.github.omisie11.coronatrackerplayground.data.remote.model.GlobalSummaryRemote
import io.github.omisie11.coronatrackerplayground.util.PREFS_LAST_REFRESH_GLOBAL_SUMMARY
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

@Singleton
class GlobalSummaryRepository @Inject constructor(
    private val apiService: ApiService,
    private val globalSummaryDao: GlobalSummaryDao,
    sharedPrefs: SharedPreferences
) : BaseRepository<GlobalSummaryRemote, GlobalSummary>(sharedPrefs) {

    override val lastRefreshKey: String = PREFS_LAST_REFRESH_GLOBAL_SUMMARY

    fun getGlobalSummaryFlow(): Flow<GlobalSummary> = globalSummaryDao.getGlobalSummaryFlow()

    fun getGlobalSummaryPieChartDataFlow(): Flow<List<PieEntry>> =
        globalSummaryDao.getGlobalSummaryFlow()
            .map { summary -> mapGlobalSummaryToPieChartEntry(summary) }

    override suspend fun makeApiCall(): Response<GlobalSummaryRemote> =
        apiService.getGlobalSummary()

    override suspend fun saveToDb(data: GlobalSummary) {
        globalSummaryDao.replace(data)
    }

    override suspend fun mapRemoteModelToLocal(data: GlobalSummaryRemote): GlobalSummary =
        data.mapToLocalSummary()

    private fun mapGlobalSummaryToPieChartEntry(data: GlobalSummary?): List<PieEntry> {
        return if (data != null) {
            listOf(
                PieEntry(data.confirmed?.toFloat() ?: 0F, "confirmed"),
                PieEntry(data.recovered?.toFloat() ?: 0F, "recovered"),
                PieEntry(data.deaths?.toFloat() ?: 0F, "deaths")
            )
        } else emptyList()
    }
}
