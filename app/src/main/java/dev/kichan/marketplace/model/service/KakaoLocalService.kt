package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.model.data.kakao.KakaoLocal
import dev.kichan.marketplace.model.data.kakao.adress.Address
import dev.kichan.marketplace.model.data.kakao.local.Place
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoLocalService {
    @GET("/v2/local/search/keyword.json")
    suspend fun searchKeyword(
        @Header("Authorization") key: String = "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}",
        @Query("query") query: String,
        @Query("x") x: String,
        @Query("y") y: String,
        @Query("radius") radius: Int,
        @Query("page") page: Int = 1
    ): Response<KakaoLocal<Place>>

    @GET("/v2/local/search/address.json")
    suspend fun getAddress(
        @Header("Authorization") key: String = "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}",
        @Query("query") query: String,
    ): Response<KakaoLocal<Address>>
}