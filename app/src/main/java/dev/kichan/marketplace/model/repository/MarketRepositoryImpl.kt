package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository

import android.content.Context
import android.net.Uri
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.market.Market
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.common.FileUtils
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketCreateReq
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketUpdateReq
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service.MarketService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File


class MarketRepositoryImpl : MarketRepository {
    private val marketService = NetworkModule.getService(MarketService::class.java)

    override suspend fun getMarket(id: Int): Response<ResponseTemplate<Market>>
        = marketService.getMarket(id = id)

    override suspend fun updateMarket(
        id: Int,
        body: MarketUpdateReq
    ): Response<ResponseTemplate<Market>>
        = marketService.updateMarket(id = id, body = body)

    override suspend fun deleteMarket(id: Int) = marketService.deleteMarket(id = id)

    override suspend fun createMarket(
        context : Context,
        body: MarketCreateReq,
        image : Uri
    ): Response<ResponseTemplate<Market>> {
        val filePath = FileUtils.getPath(context, image)!!

        val file = File(filePath)
        val requestFile = RequestBody.create("image/png".toMediaTypeOrNull(), file)
        val filePart = MultipartBody.Part.createFormData("images", file.name, requestFile)

        val res = marketService.createMarket(body, listOf(filePart))

        return res
    }
}