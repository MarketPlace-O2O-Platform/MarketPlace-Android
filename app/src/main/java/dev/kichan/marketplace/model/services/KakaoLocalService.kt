package dev.kichan.marketplace.model.services

import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.model.dto.kakao.KakaoLocal
import dev.kichan.marketplace.model.dto.kakao.adress.Address
import dev.kichan.marketplace.model.dto.kakao.local.Place
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoLocalService {
    @GET("/v2/local/search/keyword.json")
    suspend fun searchKeyword(
        @Query("query") query: String,
        @Query("x") x: String,
        @Query("y") y: String,
        @Query("radius") radius: Int,
        @Query("page") page: Int = 1
    ): Response<KakaoLocal<Place>>

    @GET("/v2/local/geo/coord2address.json")
    suspend fun coord2Address(
        @Query("x") x: String,
        @Query("y") y: String,
    ): Response<KakaoLocal<Address>>

    @GET("/v2/local/search/address.json")
    suspend fun getAddress(
        @Query("query") query: String,
    ): Response<KakaoLocal<Address>>
}