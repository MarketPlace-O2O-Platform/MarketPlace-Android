package dev.kichan.marketplace.common

import java.time.LocalDateTime

object MaintenanceChecker {
    // 사전 공지 기간
    private val PRE_NOTIFICATION_START = LocalDateTime.of(2025, 12, 23, 0, 0)
    private val PRE_NOTIFICATION_END = LocalDateTime.of(2025, 12, 25, 23, 59)

    // 점검 기간
    private val MAINTENANCE_START = LocalDateTime.of(2025, 12, 26, 0, 0)
    private val MAINTENANCE_END = LocalDateTime.of(2025, 12, 28, 23, 59)

    fun getMaintenanceStatus(): MaintenanceStatus {
        val now = LocalDateTime.now()
        return when (now) {
            in MAINTENANCE_START..MAINTENANCE_END -> MaintenanceStatus.InMaintenance
            in PRE_NOTIFICATION_START..PRE_NOTIFICATION_END -> MaintenanceStatus.PreNotification
            else -> MaintenanceStatus.Normal
        }
    }
}

sealed class MaintenanceStatus {
    object Normal : MaintenanceStatus()
    object PreNotification : MaintenanceStatus()
    object InMaintenance : MaintenanceStatus()
}
