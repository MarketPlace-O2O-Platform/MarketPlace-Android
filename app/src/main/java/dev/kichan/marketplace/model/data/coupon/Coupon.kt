package dev.kichan.marketplace.model.data.coupon

import java.time.LocalDate
import java.time.LocalDateTime

data class Coupon(
    val id: Long,
    val marketId: Long,         // 매장ID
    val name: String,           // 쿠폰명
    val description: String?,   // 설명 (Nullable)
    val deadline: LocalDate,       // 마감일자
    val count: Int,             // 수량
    val isHidden: Boolean,      // 숨김여부
    val isDeleted: Boolean,     // 삭제여부
    val createdAt: LocalDate,      // 생성일자
    val modifiedAt: LocalDate?     // 수정일자 (Nullable)
)