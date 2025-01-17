package io.github.omisie11.coronatrackerplayground.ui.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.PieEntry
import io.github.omisie11.coronatrackerplayground.data.local.model.LocalSummary
import io.github.omisie11.coronatrackerplayground.data.repository.LocalSummaryRepository
import io.github.omisie11.coronatrackerplayground.vo.FetchResult
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LocalViewModel @Inject constructor(private val repository: LocalSummaryRepository) :
    ViewModel() {

    private val localSummary = MutableLiveData<LocalSummary>()
    private val localPieChartData = MutableLiveData<List<PieEntry>>()
    private val isDataFetching: LiveData<Boolean> = repository.getFetchingStatus()
    private val _snackBar: MutableLiveData<FetchResult> = repository.getFetchResult()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLocalSummaryFlow().collect { localSummary.postValue(it) }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLocalSummaryPieChartDataFlow()
                .collect { localPieChartData.postValue(it) }
        }
    }

    fun getSummary(): LiveData<LocalSummary> = localSummary

    fun getLocalPieChartData(): LiveData<List<PieEntry>> = localPieChartData

    fun getDataFetchingStatus(): LiveData<Boolean> = isDataFetching

    fun refreshLocalSummary(forceRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) { repository.refreshData(forceRefresh) }
    }

    /**
     * Request a snackbar to display a string.
     */
    val snackbar: LiveData<FetchResult>
        get() = _snackBar

    /**
     * Called immediately after the UI shows the snackbar.
     */
    fun onSnackbarShown() {
        _snackBar.value = FetchResult.OK
    }
}
