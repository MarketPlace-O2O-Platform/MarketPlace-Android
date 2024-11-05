package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import retrofit2.Response

interface MemberRepository {
    suspend fun login(body : LoginReq) : Response<ResponseTemplate<LoginRes>>
}