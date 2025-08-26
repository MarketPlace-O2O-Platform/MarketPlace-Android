package dev.kichan.marketplace.model.services

import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*
import dev.kichan.marketplace.model.dto.*

interface FavoritesService {

    @POST("/api/favorites")
    suspend fun createCoupon_1(@Query("marketId") marketId: Long? = null): Response<CommonResponseObject>

}