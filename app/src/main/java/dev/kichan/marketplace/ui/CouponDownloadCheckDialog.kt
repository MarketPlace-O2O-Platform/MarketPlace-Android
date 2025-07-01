package dev.kichan.marketplace.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.kichan.marketplace.ui.component.atoms.CustomButton
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun CouponDownloadCheckDialog(
    onDismiss: () -> Unit,
    onAccept: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
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
                    text = "이 쿠폰을 받으시겠습니까?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PretendardFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // 안내 문구
                Text(
                    text = "3일 이내 사용하셔야 합니다.\n다운 받은 시점으로 3일 후에 만료됩니다",
                    fontSize = 14.sp,
                    color = Color(0xFF838A94),
                    textAlign = TextAlign.Center,
                    fontFamily = PretendardFamily,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // Yes 버튼
                CustomButton(
                    text = "Yes",
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    onAccept()
                }

                Spacer(modifier = Modifier.height(12.dp))

                CustomButton(
                    text = "No",
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color(0xffffffff),
                    border = BorderStroke(1.dp, Color(0xff303030)),
                    textColor = Color(0xff303030)
                ) {
                    onDismiss()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CouponDownloadCheckDialogPreview() {
    MarketPlaceTheme {
        CouponDownloadCheckDialog(
            onDismiss = {},
            onAccept = {}
        )
    }
}