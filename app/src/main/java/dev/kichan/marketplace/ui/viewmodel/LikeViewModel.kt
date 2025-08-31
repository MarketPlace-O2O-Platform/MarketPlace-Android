package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.TempMarketRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LikeUiState(
    val cheerTicket: Int = 0,
    val cheerTempMarkets: List<TempMarketRes> = emptyList(),
    val tempMarkets: List<TempMarketRes> = emptyList(),
    val searchTempMarket: List<TempMarketRes> = emptyList(),
    val isLoading: Boolean = false,
    val searchKey: String = "",
    val selectedCategory: LargeCategory = LargeCategory.All,
    val page: Int = 0,
    val hasNext: Boolean = true,
)

class LikeViewModel(application: Application) : AndroidViewModel(application) {

    private val membersRepository = RepositoryProvider.provideMembersRepository()
    private val tempmarketsRepository = RepositoryProvider.provideTempmarketsRepository()
    private val cheerRepository = RepositoryProvider.provideCheerRepository()

    private val _uiState = MutableStateFlow(LikeUiState())
    val uiState = _uiState.asStateFlow()

    fun getMemberInfo() {
        viewModelScope.launch {
            try {
                val response = membersRepository.getMember()
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(cheerTicket = response.body()?.response?.cheerTicket ?: 0)
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun getTempMarkets() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, page = 0, hasNext = true)
            try {
                val response = tempmarketsRepository.getTempMarket(
                    lastPageIndex = 0,
                    category = _uiState.value.selectedCategory.backendLabel
                )
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(
                        tempMarkets = response.body()?.response?.marketResDtos ?: emptyList(),
                        hasNext = response.body()?.response?.hasNext ?: false
                    )
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun loadMoreTempMarkets() {
        if (_uiState.value.isLoading || !_uiState.value.hasNext) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val nextPage = _uiState.value.page + 1
                val response = tempmarketsRepository.getTempMarket(
                    lastPageIndex = nextPage.toLong(),
                    category = _uiState.value.selectedCategory.backendLabel
                )
                if (response.isSuccessful) {
                    val newMarkets = response.body()?.response?.marketResDtos ?: emptyList()
                    _uiState.value = _uiState.value.copy(
                        tempMarkets = _uiState.value.tempMarkets + newMarkets,
                        page = nextPage,
                        hasNext = response.body()?.response?.hasNext ?: false
                    )
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun getCheerTempMarkets() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = tempmarketsRepository.getTempMarketPage()
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(cheerTempMarkets = response.body()?.response?.marketResDtos ?: emptyList())
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
    
    fun searchTempMarket(searchKey: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = tempmarketsRepository.searchMarket(name = searchKey)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(searchTempMarket = response.body()?.response?.marketResDtos ?: emptyList())
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun cheer(tempMarketId: Long) {
        viewModelScope.launch {
            try {
                val response = cheerRepository.createCheer(tempMarketId)
                if (response.isSuccessful) {
                    getMemberInfo()
                    getTempMarkets()
                    getCheerTempMarkets()
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    fun onSearchKeyChanged(searchKey: String) {
        _uiState.value = _uiState.value.copy(searchKey = searchKey)
        if (searchKey.isNotEmpty()) {
            searchTempMarket(searchKey)
        }
    }

    fun onCategoryChanged(category: LargeCategory) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
        getTempMarkets()
    }
}
