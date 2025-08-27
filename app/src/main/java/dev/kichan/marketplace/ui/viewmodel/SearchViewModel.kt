package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.MarketRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SearchUiState(
    val searchKey: String = "",
    val recentKeywords: List<String> = listOf(
        "신복관",
        "송쭈집",
        "우정소갈비",
        "디저트39",
        "헬스장",
        "필라테스"
    ),
    val searchResults: List<MarketRes> = emptyList(),
    val isLoading: Boolean = false,
    val isFirstSearch: Boolean = true,
)

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val marketsRepository = RepositoryProvider.provideMarketsRepository()

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun onSearchKeyChanged(key: String) {
        _uiState.value = _uiState.value.copy(searchKey = key)
        if (key.length >= 2) {
            searchMarkets(key)
        } else {
            _uiState.value = _uiState.value.copy(searchResults = emptyList(), isFirstSearch = true)
        }
    }

    private fun searchMarkets(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = marketsRepository.searchMarket_1(name = query)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(
                        searchResults = response.body()?.response?.marketResDtos ?: emptyList(),
                        isFirstSearch = false
                    )
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}
