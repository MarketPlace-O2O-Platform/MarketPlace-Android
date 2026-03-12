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
import dev.kichan.marketplace.common.AnnouncementData
import dev.kichan.marketplace.ui.component.atoms.CustomButton
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun AnnouncementDialog(
    data: AnnouncementData,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    val annotatedMessage = buildAnnotatedString {
        append(data.message)

        if (data.linkUrl.isNotBlank()) {
            append("\n\n")
            pushStringAnnotation(tag = "URL", annotation = data.linkUrl)
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF4A90E2),
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(data.linkUrl)
            }
            pop()
        }
    }

    Dialog(
        onDismissRequest = { if (data.dismissible) onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = data.dismissible,
            dismissOnClickOutside = data.dismissible
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
                    text = data.title,
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
                    text = data.buttonText,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (data.dismissible) {
                        onDismiss()
                    } else {
                        (context as? Activity)?.finishAffinity()
                    }
                }
            }
        }
    }
}
