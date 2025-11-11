package dev.kichan.marketplace.model.services

import dev.kichan.marketplace.model.dto.kakao.KakaoLocal
import dev.kichan.marketplace.model.dto.kakao.adress.Address
import dev.kichan.marketplace.model.dto.kakao.local.Place
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoLocalService {
    @GET("/v2/local/search/keyword.json")
    suspend fun searchKeyword(
        @Query("query") query: String,
        @Query("category_group_code") categoryGroupCode: String? = null,
        @Query("x") x: String? = null,
        @Query("y") y: String? = null,
        @Query("radius") radius: Int? = null,
        @Query("page") page: Int = 1
    ): Response<KakaoLocal<Place>>

    @GET("/v2/local/geo/coord2address.json")
    suspend fun coord2Address(
        @Query("x") x: String,
        @Query("y") y: String,
    ): Response<KakaoLocal<Address>>

    @GET("/v2/local/geo/transcoord.json")
    suspend fun transCoord(
        @Query("x") x: String,
        @Query("y") y: String,
        @Query("input_coord") inputCoord: String,
        @Query("output_coord") outputCoord: String,
    ): Response<KakaoLocal<Address>>

    @GET("/v2/local/search/address.json")
    suspend fun getAddress(
        @Query("query") query: String,
    ): Response<KakaoLocal<Address>>
}