package dev.kichan.marketplace.di

import android.app.Application
import dagger.Module
dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kichan.marketplace.model.repository.AuthRepositoryImpl
import dev.kichan.marketplace.model.repository.CheerRepository
import dev.kichan.marketplace.model.repository.CouponMemberRepository
import dev.kichan.marketplace.model.repository.CouponMemberRepositoryImpl
import dev.kichan.marketplace.model.repository.CouponOwnerRepository
import dev.kichan.marketplace.model.repository.CouponRepository
import dev.kichan.marketplace.model.repository.CouponUserRepository
import dev.kichan.marketplace.model.repository.FavoritesRepository
import dev.kichan.marketplace.model.repository.MarkerLikeRepository
import dev.kichan.marketplace.model.repository.MarketOwnerRepository
import dev.kichan.marketplace.model.repository.MarketRepository
import dev.kichan.marketplace.model.repository.MemberRepository
import dev.kichan.marketplace.model.repository.PayBackCouponMemberRepository
import dev.kichan.marketplace.model.repository.PaybackCouponsManageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMemberRepository(application: Application): MemberRepository {
        return MemberRepositoryImpl(NetworkModule.getService(MemberService::class.java))
    }

    @Provides
    @Singleton
    fun provideCheerRepository(): CheerRepository {
        return CheerRepository()
    }

    @Provides
    @Singleton
    fun provideCouponMemberRepository(): CouponMemberRepository {
        return CouponMemberRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideCouponOwnerRepository(): CouponOwnerRepository {
        return CouponOwnerRepository()
    }

    @Provides
    @Singleton
    fun provideCouponRepository(): CouponRepository {
        return CouponRepository()
    }

    @Provides
    @Singleton
    fun provideCouponUserRepository(): CouponUserRepository {
        return CouponUserRepository()
    }

    @Provides
    @Singleton
    fun provideFavoritesRepository(): FavoritesRepository {
        return FavoritesRepository()
    }

    @Provides
    @Singleton
    fun provideMarkerLikeRepository(): MarkerLikeRepository {
        return MarkerLikeRepository()
    }

    @Provides
    @Singleton
    fun provideMarketOwnerRepository(): MarketOwnerRepository {
        return MarketOwnerRepository()
    }

    @Provides
    @Singleton
    fun provideMarketRepository(): MarketRepository {
        return MarketRepository()
    }

    @Provides
    @Singleton
    fun providePayBackCouponMemberRepository(): PayBackCouponMemberRepository {
        return PayBackCouponMemberRepository()
    }

    @Provides
    @Singleton
    fun providePaybackCouponsManageRepository(): PaybackCouponsManageRepository {
        return PaybackCouponsManageRepository()
    }
}