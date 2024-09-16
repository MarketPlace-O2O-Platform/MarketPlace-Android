package dev.kichan.marketplace.model.data.market

import java.time.LocalDateTime

// 매장 (Market)
data class Market(
    val id: Long,
    val categoryId: Long,           // 카테고리ID
    val name: String,               // 매장명
    val description: String?,       // 설명 (nullable)
    val operationHours: String?,    // 운영시간
    val closedDays: String?,        // 휴무일
    val phone: String?,             // 전화번호
    val address: String?,           // 매장주소
    val thumbnail: String?,         // 대표이미지
    val createdAt: LocalDateTime,   // 생성일자
    val modifiedAt: LocalDateTime?  // 수정일자 (nullable)
)