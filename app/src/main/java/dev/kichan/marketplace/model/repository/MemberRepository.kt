package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import dev.kichan.marketplace.model.data.login.MemberLoginRes
import retrofit2.Response

interface MemberRepository {
    suspend fun login(body : LoginReq) : Response<ResponseTemplate<String>>
    suspend fun logout()
    suspend fun getMemberData() : Response<ResponseTemplate<MemberLoginRes>>

    suspend fun saveFCMToken(
        token: String
    ): Response<ResponseTemplate<Unit>>
}