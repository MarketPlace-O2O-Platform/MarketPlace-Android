package dev.kichan.marketplace.ui.page

enum class NotificationType(val displayName: String, val serverType: String?) {
    ALL("전체", null),
    MARKET("쿠폰 발급", "MARKET"),
    COUPON("쿠폰 만료", "COUPON"),
    NOTICE("공지", "NOTICE");

    companion object {
        fun fromServerType(serverType: String?): NotificationType {
            return values().find { it.serverType == serverType } ?: ALL
        }
    }
}