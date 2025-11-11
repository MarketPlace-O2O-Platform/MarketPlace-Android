package dev.kichan.marketplace.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dev.kichan.marketplace.BuildConfig
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.MarketRes
import dev.kichan.marketplace.model.dto.kakao.local.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchUiState(
    val key: String = "",
    val recentKeywords: List<String> = emptyList(),
    val isFirst: Boolean = true,
    val result: List<MarketRes> = emptyList(),
)

class SearchViewModel : ViewModel() {
    private val marketsRepository = RepositoryProvider.provideMarketsRepository()

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun setKey(key: String) {
        _uiState.update { it.copy(key = key) }
    }

    fun search() {
        viewModelScope.launch {
            try {
                val response = marketsRepository.searchMarket_1(
                    lastPageIndex = null,
                    pageSize = null,
                    name = _uiState.value.key
                )
                if (response.isSuccessful) {
                    val result = response.body()?.response?.marketResDtos ?: emptyList()
                    _uiState.update { it.copy(result = result, isFirst = result.isEmpty()) }
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("SearchViewModel", "매장 검색 실패: key=${_uiState.value.key}", e)
                }
                _uiState.update { it.copy(result = emptyList(), isFirst = true) }
            }
        }
    }

    fun getRecentKeywords() {
        // TODO: Implement logic to get recent keywords
        _uiState.update { it.copy(recentKeywords = listOf("신복관", "송쭈집", "우정소갈비", "디저트39", "헬스장", "필라테스")) }
    }
}