package dev.kichan.marketplace.model.repository

import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import dev.kichan.marketplace.model.services.CheerService
import dev.kichan.marketplace.model.dto.*

@Singleton
class CheerRepository @Inject constructor(
    private val service: CheerService
) {

    suspend fun createCheer(@Query("tempMarketId") tempMarketId: Long? = null): Response<CommonResponseObject> {
        return service.createCheer(tempMarketId)
    }

}