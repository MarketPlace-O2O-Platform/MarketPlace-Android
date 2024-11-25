package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.market.Market
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketCreateReq
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketUpdateReq
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface MarketService {
    @GET("/api/owners/markets/{marketId}")
    suspend fun getMarket(
        @Path("marketId") id: Int
    ): Response<ResponseTemplate<Market>>

    @PUT("/api/owners/markets/{marketId}")
    suspend fun updateMarket(
        @Path("marketId") id: Int,
        @Body body: MarketUpdateReq
    ): Response<ResponseTemplate<Market>>

    @DELETE("/api/owners/markets/{marketId}")
    suspend fun deleteMarket(
        @Path("marketId") id: Int
    )

    @Multipart
    @POST("/api/owners/markets")
    suspend fun createMarket(
        @Part("jsonData") body: MarketCreateReq,
        @Part images : List<MultipartBody.Part>
    ) : Response<ResponseTemplate<Market>>
}