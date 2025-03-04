package dev.kichan.marketplace.model.data.Member

import java.time.LocalDateTime

// 발급쿠폰 (MemberCoupon)
data class MemberCoupon(
    val id: Long,
    val memberId: Long,             // 회원ID
    val eventId: Long,              // 쿠폰ID
    val isUse: Boolean,             // 사용여부
    val createdAt: LocalDateTime,   // 생성일자
    val modifiedAt: LocalDateTime?  // 수정일자 (nullable)
)