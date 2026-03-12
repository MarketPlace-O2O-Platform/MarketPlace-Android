package dev.kichan.marketplace.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Firebase Remote Config에서 받아오는 공지 데이터
 *
 * @property enabled 공지 활성화 여부
 * @property type 공지 타입 (notice / maintenance / update)
 * @property title 다이얼로그 제목
 * @property message 본문 내용
 * @property buttonText 버튼 텍스트
 * @property linkUrl 클릭 가능한 링크 (빈 문자열이면 미표시)
 * @property dismissible 닫기 가능 여부 (false면 앱 종료만)
 * @property startTime 시작 시간 (ISO 8601, 빈 문자열이면 즉시)
 * @property endTime 종료 시간 (ISO 8601, 빈 문자열이면 무기한)
 */
data class AnnouncementData(
    val enabled: Boolean = false,
    val type: String = "notice",
    val title: String = "",
    val message: String = "",
    val buttonText: String = "확인",
    val linkUrl: String = "",
    val dismissible: Boolean = true,
    val startTime: String = "",
    val endTime: String = ""
) {
    companion object {
        private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    }

    /**
     * 현재 시간 기준으로 공지를 표시해야 하는지 판단
     */
    fun shouldShow(): Boolean {
        if (!enabled) return false

        val now = LocalDateTime.now()

        // startTime 체크 (빈 문자열이면 즉시 시작)
        if (startTime.isNotBlank()) {
            try {
                val start = LocalDateTime.parse(startTime, formatter)
                if (now.isBefore(start)) return false
            } catch (e: DateTimeParseException) {
                // 파싱 실패 시 시작 시간 체크 생략
            }
        }

        // endTime 체크 (빈 문자열이면 무기한)
        if (endTime.isNotBlank()) {
            try {
                val end = LocalDateTime.parse(endTime, formatter)
                if (now.isAfter(end)) return false
            } catch (e: DateTimeParseException) {
                // 파싱 실패 시 종료 시간 체크 생략
            }
        }

        return true
    }
}
