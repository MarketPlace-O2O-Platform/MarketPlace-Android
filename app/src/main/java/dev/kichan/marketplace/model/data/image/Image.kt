package dev.kichan.marketplace.model.data.image

// 사진 (Image)
data class Image(
    val id: Long,
    val marketId: Long,             // 매장ID
    val uuid: String,               // 파일UUID
    val folder: String?,            // 저장 폴더 이름 (nullable)
    val originalName: String,       // 원본 파일명
)