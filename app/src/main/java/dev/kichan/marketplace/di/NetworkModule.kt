package dev.kichan.marketplace.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.model.data.store.AuthDataStore
import dev.kichan.marketplace.model.service.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = BuildConfig.API_BASE_URL
    private const val KAKAO_API_BASE_URL = "https://dapi.kakao.com/"

    @Provides
    @Singleton
    fun provideAuthDataStore(application: Application): AuthDataStore {
        return AuthDataStore(application)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @Named("DefaultInterceptor")
    fun provideDefaultInterceptor(authDataStore: AuthDataStore): Interceptor {
        return Interceptor { chain ->
            val token = runBlocking { authDataStore.authToken.first() }
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    @Named("KakaoInterceptor")
    fun provideKakaoInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}")
                .build()
            chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    @Named("DefaultOkHttpClient")
    fun provideDefaultOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @Named("DefaultInterceptor") defaultInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(defaultInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("KakaoOkHttpClient")
    fun provideKakaoOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @Named("KakaoInterceptor") kakaoInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(kakaoInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("DefaultRetrofit")
    fun provideDefaultRetrofit(@Named("DefaultOkHttpClient") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("KakaoRetrofit")
    fun provideKakaoRetrofit(@Named("KakaoOkHttpClient") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(KAKAO_API_BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideCheerService(@Named("DefaultRetrofit") retrofit: Retrofit): CheerService {
        return retrofit.create(CheerService::class.java)
    }

    @Provides
    @Singleton
    fun provideCouponApiService(@Named("DefaultRetrofit") retrofit: Retrofit): CouponApiService {
        return retrofit.create(CouponApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCouponMembersService(@Named("DefaultRetrofit") retrofit: Retrofit): CouponMembersService {
        return retrofit.create(CouponMembersService::class.java)
    }

    @Provides
    @Singleton
    fun provideCouponOwnerService(@Named("DefaultRetrofit") retrofit: Retrofit): CouponOwnerService {
        return retrofit.create(CouponOwnerService::class.java)
    }

    @Provides
    @Singleton
    fun provideCouponService(@Named("DefaultRetrofit") retrofit: Retrofit): CouponService {
        return retrofit.create(CouponService::class.java)
    }

    @Provides
    @Singleton
    fun provideCouponUserService(@Named("DefaultRetrofit") retrofit: Retrofit): CouponUserService {
        return retrofit.create(CouponUserService::class.java)
    }

    @Provides
    @Singleton
    fun provideFavoritesService(@Named("DefaultRetrofit") retrofit: Retrofit): FavoritesService {
        return retrofit.create(FavoritesService::class.java)
    }

    @Provides
    @Singleton
    fun provideKakaoLocalService(@Named("KakaoRetrofit") retrofit: Retrofit): KakaoLocalService {
        return retrofit.create(KakaoLocalService::class.java)
    }

    @Provides
    @Singleton
    fun provideMarkerLikeMemberService(@Named("DefaultRetrofit") retrofit: Retrofit): MarkerLikeMemberService {
        return retrofit.create(MarkerLikeMemberService::class.java)
    }

    @Provides
    @Singleton
    fun provideMarketOwnerService(@Named("DefaultRetrofit") retrofit: Retrofit): MarketOwnerService {
        return retrofit.create(MarketOwnerService::class.java)
    }

    @Provides
    @Singleton
    fun provideMarketService(@Named("DefaultRetrofit") retrofit: Retrofit): MarketService {
        return retrofit.create(MarketService::class.java)
    }

    @Provides
    @Singleton
    fun provideMemberService(@Named("DefaultRetrofit") retrofit: Retrofit): MemberService {
        return retrofit.create(MemberService::class.java)
    }

    @Provides
    @Singleton
    fun providePayBackCouponMember(@Named("DefaultRetrofit") retrofit: Retrofit): PayBackCouponMember {
        return retrofit.create(PayBackCouponMember::class.java)
    }

    @Provides
    @Singleton
    fun providePaybackCouponsManage(@Named("DefaultRetrofit") retrofit: Retrofit): PaybackCouponsManage {
        return retrofit.create(PaybackCouponsManage::class.java)
    }
}