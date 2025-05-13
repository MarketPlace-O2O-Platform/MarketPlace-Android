package dev.kichan.marketplace.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.data.like.TempMarketRes
import dev.kichan.marketplace.model.repository.CheerRepository
import dev.kichan.marketplace.model.repository.MarkerLikeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class LikePageUiState(
    val tempMarkets : List<TempMarketRes> = emptyList(),
    val cheerTempMarkets : List<TempMarketRes> = emptyList(),
    val searchTempMarket: List<TempMarketRes> = emptyList(),
    val isLoading: Boolean = false
)

class TempMarketViewModel : ViewModel() {
    private val tempMarketRepo = MarkerLikeRepository()
    private val cheerRepo = CheerRepository()

    var likePageState by mutableStateOf(LikePageUiState())

    fun getCheerTempMarket() = viewModelScope.launch {
        likePageState = likePageState.copy(isLoading = true)
        val res = withContext(Dispatchers.IO) { tempMarketRepo.getCheerMarket() }
        if (res.isSuccessful) {
            likePageState = likePageState.copy(
                cheerTempMarkets = res.body()!!.response.marketResDtos,
                isLoading = false
            )
        }
    }

    fun getTempMarket(selectedCategory : LargeCategory) = viewModelScope.launch {
        likePageState = likePageState.copy(isLoading = true)
        val res = withContext(Dispatchers.IO) {
            tempMarketRepo.getTempMarkets(100, selectedCategory)
        }
        if (res.isSuccessful) {
            likePageState = likePageState.copy(
                tempMarkets = res.body()!!.response.marketResDtos
            )
        }
    }

    fun onCheer(id: Long) = viewModelScope.launch {
        likePageState = likePageState.copy(isLoading = true)
        val res = withContext(Dispatchers.IO) { cheerRepo.cheer(id) }
        if (res.isSuccessful) {
            likePageState = likePageState.copy(
                tempMarkets = likePageState.tempMarkets.map { if(it.id == id) it.copy(isCheer = true, cheerCount = it.cheerCount + 1) else it.copy() },
                cheerTempMarkets = likePageState.cheerTempMarkets.map { if(it.id == id) it.copy(isCheer = true, cheerCount = it.cheerCount + 1) else it.copy() },
            )
        }
    }

    fun searchTempMarket(key : String) {
        likePageState = likePageState.copy(isLoading = true)

        viewModelScope.launch {
            val res = tempMarketRepo.getMarketSearch(key)
            if(!res.isSuccessful) {
                return@launch
            }

            val body = res.body()!!
            likePageState = likePageState.copy(
                searchTempMarket = body.response.marketResDtos,
                isLoading = false
            )
        }
    }
}