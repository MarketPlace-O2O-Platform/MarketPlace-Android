package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import dev.kichan.marketplace.model.service.MemberService
import retrofit2.Response

class MemberRepositoryImpl : MemberRepository {
    val service = NetworkModule.getService(MemberService::class.java)

    override suspend fun login(body: LoginReq): Response<ResponseTemplate<LoginRes>>
        = service.login(body)

    override suspend fun getMemberData(id: Int): Response<ResponseTemplate<LoginRes>>
        = service.getUserData(id)
}