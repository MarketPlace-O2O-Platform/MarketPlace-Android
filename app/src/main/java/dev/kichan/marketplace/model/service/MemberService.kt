package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberService {
    @POST("api/members")
    suspend fun login(@Body body : LoginReq) : Response<ResponseTemplate<LoginRes>>
}