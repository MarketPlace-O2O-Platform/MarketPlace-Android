package dev.kichan.marketplace.model.repository

import android.content.Context
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.removeAuthToken

class AuthRepository(private val context: Context) {

    suspend fun logout(): Boolean {
        // DataStore에 저장된 토큰 삭제
        removeAuthToken(context)
        // NetworkModule의 토큰도 제거하여 이후 요청에 인증 헤더가 포함되지 않도록 함
        NetworkModule.updateToken(null)
        return true
    }
}
