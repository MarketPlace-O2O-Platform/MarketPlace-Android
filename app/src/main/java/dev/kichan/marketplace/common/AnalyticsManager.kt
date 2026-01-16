package dev.kichan.marketplace.common

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Firebase Analytics 이벤트 로깅 관리
 *
 * 핵심 이벤트:
 * - login: 로그인 성공
 * - coupon_download: 쿠폰 발급
 * - coupon_use: 쿠폰 사용
 * - cheer: 공감하기
 */
object AnalyticsManager {
    private lateinit var analytics: FirebaseAnalytics

    fun init(context: Context) {
        analytics = FirebaseAnalytics.getInstance(context)
    }

    /**
     * 로그인 성공 이벤트
     */
    fun logLogin() {
        analytics.logEvent(FirebaseAnalytics.Event.LOGIN, Bundle().apply {
            putString(FirebaseAnalytics.Param.METHOD, "student_id")
        })
    }

    /**
     * 쿠폰 발급 이벤트
     * @param couponId 쿠폰 ID
     * @param couponType 쿠폰 타입 (GIFT / PAYBACK)
     * @param marketId 매장 ID
     */
    fun logCouponDownload(couponId: Long, couponType: String, marketId: Long) {
        analytics.logEvent("coupon_download", Bundle().apply {
            putLong("coupon_id", couponId)
            putString("coupon_type", couponType)
            putLong("market_id", marketId)
        })
    }

    /**
     * 쿠폰 사용 이벤트
     * @param couponId 쿠폰 ID
     * @param couponType 쿠폰 타입 (GIFT / PAYBACK)
     */
    fun logCouponUse(couponId: Long, couponType: String) {
        analytics.logEvent("coupon_use", Bundle().apply {
            putLong("coupon_id", couponId)
            putString("coupon_type", couponType)
        })
    }

    /**
     * 공감하기 이벤트
     * @param tempMarketId 공감매장 ID
     */
    fun logCheer(tempMarketId: Long) {
        analytics.logEvent("cheer", Bundle().apply {
            putLong("temp_market_id", tempMarketId)
        })
    }
}
