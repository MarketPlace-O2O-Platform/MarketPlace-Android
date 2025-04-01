package dev.kichan.marketplace.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.kichan.marketplace.model.data.market.MarketRes

sealed class MarketListPageUi {
    data object Idle : MarketListPageUi()
    data object Loading : MarketListPageUi()
    data class Success(val data: List<MarketRes>) : MarketListPageUi()
    data class Error(val message: String) : MarketListPageUi()
}

class MarketViewModel : ViewModel() {
    var marketListPageUi by mutableStateOf<MarketListPageUi>(MarketListPageUi.Idle)


}