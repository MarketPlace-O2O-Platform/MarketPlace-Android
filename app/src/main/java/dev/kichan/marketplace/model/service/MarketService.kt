package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.market.Market
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketCreateReq
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MarketService {
    @Multipart
    @POST("api/owners/markets")
    suspend fun createMarket(
        @Part("jsonData") body: MarketCreateReq,
        @Part file : MultipartBody.Part
    ) : Response<ResponseTemplate<Market>>
}