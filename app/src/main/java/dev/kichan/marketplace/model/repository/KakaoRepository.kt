package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.dto.kakao.KakaoLocal
import dev.kichan.marketplace.model.dto.kakao.adress.Address
import dev.kichan.marketplace.model.services.KakaoLocalService
import retrofit2.Response

class KakaoRepository(private val kakaoLocalService: KakaoLocalService) {
    suspend fun coord2Address(x: String, y: String): Response<KakaoLocal<Address>> {
        return kakaoLocalService.coord2Address(x, y)
    }

    suspend fun getAddress(query: String): Response<KakaoLocal<Address>> {
        return kakaoLocalService.getAddress(query)
    }
}
