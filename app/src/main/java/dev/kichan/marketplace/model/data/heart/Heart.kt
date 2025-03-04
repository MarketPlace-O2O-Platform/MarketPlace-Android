package dev.kichan.marketplace.model.data.heart

import java.time.LocalDateTime

// 찜 (Heart)
data class Heart(
    val id: Long,
    val memberId: Long,             // 회원ID
    val marketId: Long,             // 매장ID
    val isDeleted: Boolean,         // 삭제여부
    val createdAt: LocalDateTime,   // 생성일자
    val modifiedAt: LocalDateTime?  // 수정일자 (nullable)
)