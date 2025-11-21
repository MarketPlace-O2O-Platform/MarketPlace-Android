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

    init {
        getMemberInfo()
        getCheerTempMarkets()
        getTempMarkets()
    }

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
            try {
                Log.d("CheerDebug", "=== 공감 시작 - marketId: $tempMarketId ===")
                val response = cheerRepository.createCheer(tempMarketId)

                Log.d("CheerDebug", "응답 수신 - code: ${response.code()}, isSuccessful: ${response.isSuccessful}")
                Log.d("CheerDebug", "응답 body: ${response.body()}")
                Log.d("CheerDebug", "응답 errorBody: ${response.errorBody()?.string()}")

                if (response.isSuccessful) {
                    Log.d("CheerDebug", "✓ Success 블록 진입")
                    val newCheerCount = response.body()?.response?.cheerCount ?: 0
                    Log.d("CheerDebug", "새로운 공감 수: $newCheerCount")

                    // 1. tempMarkets에서 해당 매장만 업데이트 (스크롤 위치 유지)
                    Log.d("CheerDebug", "1. tempMarkets 업데이트 시작 (현재 개수: ${_uiState.value.tempMarkets.size})")
                    _uiState.value = _uiState.value.copy(
                        tempMarkets = _uiState.value.tempMarkets.map { market ->
                            if (market.marketId == tempMarketId) {
                                market.copy(cheerCount = newCheerCount, isCheer = true)
                            } else market
                        }
                    )
                    Log.d("CheerDebug", "✓ tempMarkets 업데이트 완료")

                    // 2. 14개 이상이면 달성 임박에 추가
                    if (newCheerCount >= 14) {
                        Log.d("CheerDebug", "2. 달성 임박 추가 시작 (cheerCount >= 14)")
                        val targetMarket = _uiState.value.tempMarkets.find { it.marketId == tempMarketId }
                        Log.d("CheerDebug", "타겟 매장 찾기: ${targetMarket != null}")

                        targetMarket?.let { market ->
                            val updatedMarket = market.copy(cheerCount = newCheerCount, isCheer = true)
                            // 중복 방지: 이미 있는지 확인
                            val alreadyExists = _uiState.value.cheerTempMarkets.any { it.marketId == tempMarketId }
                            Log.d("CheerDebug", "cheerTempMarkets에 이미 존재: $alreadyExists")

                            if (!alreadyExists) {
                                _uiState.value = _uiState.value.copy(
                                    cheerTempMarkets = _uiState.value.cheerTempMarkets + updatedMarket
                                )
                                Log.d("CheerDebug", "✓ cheerTempMarkets에 추가 완료")
                            } else {
                                // 이미 있으면 업데이트
                                _uiState.value = _uiState.value.copy(
                                    cheerTempMarkets = _uiState.value.cheerTempMarkets.map {
                                        if (it.marketId == tempMarketId) updatedMarket else it
                                    }
                                )
                                Log.d("CheerDebug", "✓ cheerTempMarkets 업데이트 완료")
                            }
                        }
                    }

                    // 3. 공감권만 업데이트 (API 1번만)
                    Log.d("CheerDebug", "3. getMemberInfo() 호출")
                    getMemberInfo()
                    Log.d("CheerDebug", "=== 공감 완료 ===")
                } else {
                    Log.d("CheerDebug", "✗ Success 블록 진입 실패 - isSuccessful: false")
                    _snackbarMessage.emit("공감권이 소진되었습니다")
                }
            } catch (e: Exception) {
                Log.e("CheerDebug", "✗✗✗ Exception 발생: ${e.javaClass.simpleName}", e)
                Log.e("CheerDebug", "Exception 메시지: ${e.message}")
                Log.e("CheerDebug", "Stack trace:", e)
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
