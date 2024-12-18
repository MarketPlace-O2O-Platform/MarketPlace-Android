package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository

import android.content.Context
import android.net.Uri
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.market.Market
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketCreateReq
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketUpdateReq
import retrofit2.Response

interface MarketOwnerRepository {
    suspend fun getMarket(id: Int): Response<ResponseTemplate<Market>>
    suspend fun updateMarket(id: Int, body: MarketUpdateReq): Response<ResponseTemplate<Market>>
    suspend fun deleteMarket(id: Int)
    suspend fun createMarket(
        context : Context,
        body: MarketCreateReq,
        image : Uri,
    ): Response<ResponseTemplate<Market>>
}