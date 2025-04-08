package dev.kichan.marketplace.model

import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.model.service.CouponApiService
import dev.kichan.marketplace.model.service.KakaoLocalService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.runner.manipulation.Ordering.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TokenStore {
    var token: String? = null
}

class AuthInterceptor(val type : String = "default") : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = if(type == "default") "Bearer ${TokenStore.token}" else "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}"

        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(newRequest)
    }
}

//todo: 나중에 토큰 가져오는 이거 좀...

object NetworkModule {
    private const val BASE_URL = BuildConfig.API_BASE_URL
    private const val KAKAO_API_BASE_URL = "https://dapi.kakao.com/"

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun updateToken(token: String?) {
        TokenStore.token = token
    }

    private fun getClient(type : String = "default") : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(type))
            .addNetworkInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

//    private val client: OkHttpClient
//        get() = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .addInterceptor(authInterceptor)
//            .addNetworkInterceptor(interceptor)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .build()

    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(getClient())
            .build()


    private val kakaoRetrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(KAKAO_API_BASE_URL)
        .client(getClient("kakao"))
        .build()

    fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }

    // ✅ 4. CouponApiService 추가 (기존 getService 활용)
    fun getCouponService(): CouponApiService {
        return retrofit.create(CouponApiService::class.java)
    }

    fun getKakaoService(): KakaoLocalService {
        return kakaoRetrofit.create(KakaoLocalService::class.java)
    }

    fun getImage(id: String, isTempMarket : Boolean = false): String =
        if(isTempMarket)
            "${BASE_URL}image/tempMarket/${id}"
        else
            "${BASE_URL}image/${id}"

    fun getImageModel(context: android.content.Context, url: String): ImageRequest =
        ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .build()
}