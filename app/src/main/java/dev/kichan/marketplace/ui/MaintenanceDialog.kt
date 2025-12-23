package dev.kichan.marketplace.ui

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.kichan.marketplace.common.MaintenanceStatus
import dev.kichan.marketplace.ui.component.atoms.CustomButton
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun MaintenanceDialog(
    status: MaintenanceStatus,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    // 디자인팀 제공 문구
    val title = "📢 쿠러미 이용 안내"
    val messageText = """교내 서버실 작업으로 인해
이번 주 금요일부터 일요일까지 쿠러미 앱 접속이 일시적으로 제한될 예정입니다.
이용에 불편을 드려 죄송합니다.

환급 관련하여
영수증·계좌 등록 후 2일 이상 입금이 지연될 경우,
아래 카카오채널로 문의 부탁드립니다."""

    val linkUrl = "http://pf.kakao.com/_XkZnn/chat"

    val closingText = """

항상 쿠러미를 이용해주셔서 감사드리며,
더 안정적인 서비스로 찾아뵙겠습니다. 🙏"""

    // 클릭 가능한 링크를 포함한 AnnotatedString 생성
    val annotatedMessage = buildAnnotatedString {
        append(messageText)
        append("\n")

        // 링크 부분
        pushStringAnnotation(tag = "URL", annotation = linkUrl)
        withStyle(
            style = SpanStyle(
                color = Color(0xFF4A90E2),
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(linkUrl)
        }
        pop()

        append(closingText)
    }

    val buttonText = if (status is MaintenanceStatus.PreNotification) "확인" else "앱 종료"
    val canDismiss = status is MaintenanceStatus.PreNotification

    Dialog(
        onDismissRequest = { if (canDismiss) onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = canDismiss,
            dismissOnClickOutside = canDismiss
        )
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PretendardFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                ClickableText(
                    text = annotatedMessage,
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFF838A94),
                        textAlign = TextAlign.Center,
                        fontFamily = PretendardFamily,
                        lineHeight = 20.sp
                    ),
                    modifier = Modifier.padding(bottom = 32.dp),
                    onClick = { offset ->
                        annotatedMessage.getStringAnnotations(tag = "URL", start = offset, end = offset)
                            .firstOrNull()?.let { annotation ->
                                uriHandler.openUri(annotation.item)
                            }
                    }
                )

                CustomButton(
                    text = buttonText,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (status is MaintenanceStatus.PreNotification) {
                        onDismiss()
                    } else {
                        // 점검 중: 앱 완전 종료
                        (context as? Activity)?.finishAffinity()
                    }
                }
            }
        }
    }
}
