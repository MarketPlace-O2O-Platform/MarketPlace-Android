package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.TempMarketRes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    val hasNext: Boolean = true
)

class LikeViewModel(application: Application) : AndroidViewModel(application) {

    private val membersRepository = RepositoryProvider.provideMembersRepository()
    private val tempmarketsRepository = RepositoryProvider.provideTempmarketsRepository()
    private val cheerRepository = RepositoryProvider.provideCheerRepository()

    private val _uiState = MutableStateFlow(LikeUiState())
    val uiState = _uiState.asStateFlow()

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage.asSharedFlow()

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
                val category = _uiState.value.selectedCategory.backendLabel
                Log.d("TempMarkets", "API 호출 시작 - category: $category")

                val response = tempmarketsRepository.getTempMarket(
                    lastPageIndex = null,
                    category = category,
                    count = 10
                )

                Log.d("TempMarkets", "응답 코드: ${response.code()}, isSuccessful: ${response.isSuccessful}")
                Log.d("TempMarkets", "응답 body: ${response.body()}")

                if (response.isSuccessful) {
                    val markets = response.body()?.response?.marketResDtos ?: emptyList()
                    Log.d("TempMarkets", "받은 매장 수: ${markets.size}")

                    _uiState.value = _uiState.value.copy(
                        tempMarkets = markets,
                        hasNext = response.body()?.response?.hasNext ?: false
                    )
                } else {
                    Log.e("TempMarkets", "API 실패 - 코드: ${response.code()}, 메시지: ${response.message()}")
                    Log.e("TempMarkets", "에러 body: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("TempMarkets", "API 호출 중 예외 발생", e)
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
                val lastMarketId = _uiState.value.tempMarkets.lastOrNull()?.marketId
                val response = tempmarketsRepository.getTempMarket(
                    lastPageIndex = lastMarketId,
                    category = _uiState.value.selectedCategory.backendLabel,
                    count = 10
                )
                if (response.isSuccessful) {
                    val newMarkets = response.body()?.response?.marketResDtos ?: emptyList()
                    _uiState.value = _uiState.value.copy(
                        tempMarkets = _uiState.value.tempMarkets + newMarkets,
                        page = _uiState.value.page + 1,
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
            // 티켓 체크 (API 호출 전)
            if (_uiState.value.cheerTicket <= 0) {
                _snackbarMessage.emit("공감권이 소진되었습니다")
                return@launch
            }

            try {
                val response = cheerRepository.createCheer(tempMarketId)
                if (response.isSuccessful) {
                    getMemberInfo()
                    getTempMarkets()
                    getCheerTempMarkets()
                } else {
                    _snackbarMessage.emit("공감 처리에 실패했습니다")
                }
            } catch (e: Exception) {
                _snackbarMessage.emit("네트워크 오류가 발생했습니다")
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
