package dev.kichan.marketplace.model.dto

/**
 * 매장 상세 페이지에서 일반 쿠폰과 환급 쿠폰을 통합해서 표시하기 위한 UI 전용 데이터 클래스
 *
 * - CouponRes와 PaybackRes를 하나로 통합
 * - PaybackRes에 없는 필드는 nullable로 처리
 */
data class DisplayCoupon(
    val couponId: Long,
    val couponName: String,
    val couponDescription: String,
    val couponType: String,  // "GIFT" or "PAYBACK"
    val isMemberIssued: Boolean,
    val isHidden: Boolean,

    // 일반 쿠폰(GIFT)만 있는 필드들 - nullable
    val deadLine: String? = null,
    val isAvailable: Boolean = true,  // 환급 쿠폰은 항상 true
    val stock: Int? = null,

    // 표시용 정보
    val marketId: Long,
    val marketName: String,
    val thumbnail: String,
    val address: String,

    // 추가 정보
    val couponCreatedAt: String? = null,
    val issuedCount: Long? = null
)

/**
 * CouponRes → DisplayCoupon 변환
 */
fun CouponRes.toDisplayCoupon() = DisplayCoupon(
    couponId = couponId,
    couponName = couponName,
    couponDescription = couponDescription,
    couponType = couponType,
    isMemberIssued = isMemberIssued,
    isHidden = isHidden,
    deadLine = deadLine,
    isAvailable = isAvailable,
    stock = stock,
    marketId = marketId,
    marketName = marketName,
    thumbnail = thumbnail,
    address = address,
    couponCreatedAt = couponCreatedAt,
    issuedCount = issuedCount
)

/**
 * PaybackRes → DisplayCoupon 변환
 * PaybackRes에는 매장 정보가 없으므로 MarketDetailsRes에서 가져옴
 */
fun PaybackRes.toDisplayCoupon(marketData: MarketDetailsRes) = DisplayCoupon(
    couponId = couponId,
    couponName = couponName,
    couponDescription = couponDescription,
    couponType = couponType,
    isMemberIssued = isMemberIssued,
    isHidden = isHidden,
    deadLine = null,  // 환급 쿠폰은 마감일 없음
    isAvailable = true,  // 환급 쿠폰은 항상 사용 가능
    stock = null,
    marketId = marketData.marketId,
    marketName = marketData.name,
    thumbnail = marketData.imageResList.firstOrNull()?.name ?: "",
    address = marketData.address,
    couponCreatedAt = null,
    issuedCount = null
)
