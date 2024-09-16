package dev.kichan.marketplace.model.data.Member

import java.time.LocalDateTime

// 회원 (Member)
data class Member(
    val id: Long,
    val createdAt: LocalDateTime,   // 생성일자
    val modifiedAt: LocalDateTime?  // 수정일자 (nullable)
)