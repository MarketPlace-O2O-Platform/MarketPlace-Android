package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.Member.Member
import dev.kichan.marketplace.model.data.Member.SaveFCMTokenReq
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import dev.kichan.marketplace.model.data.login.MemberLoginRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface MemberService {
    @POST("api/members")
    suspend fun login(@Body body : LoginReq) : Response<ResponseTemplate<String>>

    @GET("api/members")
    suspend fun getUserData() : Response<ResponseTemplate<MemberLoginRes>>

    @PATCH("/api/members/notification/permit")
    suspend fun saveFcmToken(
        @Body body: SaveFCMTokenReq
    ): Response<ResponseTemplate<Unit>>
}