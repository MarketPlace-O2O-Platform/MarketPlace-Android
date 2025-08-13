package dev.kichan.marketplace.model.repository

import android.content.Context
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.Member.SaveAccountReq
import dev.kichan.marketplace.model.data.Member.SaveFCMTokenReq
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.MemberLoginRes
import dev.kichan.marketplace.model.removeAuthToken
import dev.kichan.marketplace.model.service.MemberService
import retrofit2.Response

class AuthRepositoryImpl(
    private val context: Context
) : MemberRepository {
    private val service = NetworkModule.getService(MemberService::class.java)

    override suspend fun login(body: LoginReq): Response<ResponseTemplate<String>> =
        service.login(body)

    override suspend fun logout() {
        removeAuthToken(context)
        NetworkModule.updateToken(null)
    }

    override suspend fun getMemberData(): Response<ResponseTemplate<MemberLoginRes>> =
        service.getUserData()

    override suspend fun saveFCMToken(token: String): Response<ResponseTemplate<Unit>> =
        service.saveFcmToken(SaveFCMTokenReq(token))

    override suspend fun saveAccountPermit(body: SaveAccountReq) = service.saveAccount(body)

    override suspend fun saveAccountDeny() = service.deleteAccount()
}