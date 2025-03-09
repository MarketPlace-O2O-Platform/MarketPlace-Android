package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MemberService {
    @POST("api/members")
    suspend fun login(@Body body : LoginReq) : Response<ResponseTemplate<String>>

    @GET("api/members/{memberId}")
    suspend fun getUserData(
        @Path("memberId") id : Int
    ) : Response<ResponseTemplate<LoginRes>>
}

