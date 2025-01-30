package dev.kichan.marketplace

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.market.MarketCreateReq
import dev.kichan.marketplace.model.service.MarketOwnerService
import kotlinx.coroutines.launch
import org.junit.runner.manipulation.Ordering.Context

sealed class MarketState {
    data class MarketCreateState(val imageList : List<Uri>) : MarketState()
}

class MarketViewModel : ViewModel() {
    private val marketOwnerService = NetworkModule.getService(MarketOwnerService::class.java)

    fun createMarket(
        context : Context,
        image : Uri
    ) {
        val body = MarketCreateReq(
            name = "Adriana Bryan",
            description = "elementum",
            operationHours = "possim",
            closedDays = "altera",
            phoneNumber = "(239) 950-6897",
            major = "evertitur",
            address = "sanctus"
        )

        viewModelScope.launch {
            val res = marketOwnerService.createMarket(
                body = body,
                images = listOf()
            )
        }
    }
}