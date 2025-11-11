package dev.kichan.marketplace.model.data.remote

import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.model.repository.CheerRepository
import dev.kichan.marketplace.model.repository.CouponsRepository
import dev.kichan.marketplace.model.repository.FavoritesRepository
import dev.kichan.marketplace.model.repository.KakaoRepository
import dev.kichan.marketplace.model.repository.MarketsRepository
import dev.kichan.marketplace.model.repository.MembersRepository
import dev.kichan.marketplace.model.repository.NotificationRepository
import dev.kichan.marketplace.model.repository.PaybackCouponsRepository
import dev.kichan.marketplace.model.repository.RequestMarketsRepository
import dev.kichan.marketplace.model.repository.TempmarketsRepository
import dev.kichan.marketplace.model.services.CheerService
import dev.kichan.marketplace.model.services.CouponsService
import dev.kichan.marketplace.model.services.FavoritesService
import dev.kichan.marketplace.model.services.KakaoLocalService
import dev.kichan.marketplace.model.services.MarketsService
import dev.kichan.marketplace.model.services.MembersService
import dev.kichan.marketplace.model.services.NotificationService
import dev.kichan.marketplace.model.services.PaybackCouponsService
import dev.kichan.marketplace.model.services.RequestMarketsService
import dev.kichan.marketplace.model.services.TempmarketsService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepositoryProvider {

    private val retrofit = RetrofitClient.getClient()

    private val kakaoHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}")
            .build()
        chain.proceed(request)
    }.build()

    private val kakaoRetrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .client(kakaoHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val membersService = retrofit.create(MembersService::class.java)
    private val couponsService = retrofit.create(CouponsService::class.java)
    private val tempmarketsService = retrofit.create(TempmarketsService::class.java)
    private val cheerService = retrofit.create(CheerService::class.java)
    private val marketsService = retrofit.create(MarketsService::class.java)
    private val favoritesService = retrofit.create(FavoritesService::class.java)
    private val kakaoLocalService = kakaoRetrofit.create(KakaoLocalService::class.java)
    private val paybackCouponsService = retrofit.create(PaybackCouponsService::class.java)
    private val notificationService = retrofit.create(NotificationService::class.java)
    private val requestMarketsService = retrofit.create(RequestMarketsService::class.java)

    fun provideMembersRepository(): MembersRepository {
        return MembersRepository(membersService)
    }

    fun provideCouponsRepository(): CouponsRepository {
        return CouponsRepository(couponsService)
    }

    fun provideTempmarketsRepository(): TempmarketsRepository {
        return TempmarketsRepository(tempmarketsService)
    }

    fun provideCheerRepository(): CheerRepository {
        return CheerRepository(cheerService)
    }

    fun provideMarketsRepository(): MarketsRepository {
        return MarketsRepository(marketsService)
    }

    fun provideFavoritesRepository(): FavoritesRepository {
        return FavoritesRepository(favoritesService)
    }

    fun provideKakaoRepository(): KakaoRepository {
        return KakaoRepository(kakaoLocalService)
    }

    fun providePaybackCouponsRepository(): PaybackCouponsRepository {
        return PaybackCouponsRepository(paybackCouponsService)
    }

    fun provideNotificationRepository(): NotificationRepository {
        return NotificationRepository(notificationService)
    }

    fun providerRequestMarketRepository() : RequestMarketsRepository {
        return RequestMarketsRepository(requestMarketsService)
    }
}
