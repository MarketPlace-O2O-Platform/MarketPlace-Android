package dev.kichan.marketplace.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.kichan.marketplace.ui.component.atoms.CustomButton

@Composable
fun LoginDialog(onDismiss: () -> Unit, onLogin: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    "로그인이 필요한 서비스입니다.",
                    fontSize = 24.sp,
                    fontWeight = FontWeight(800)
                )
                Spacer(modifier = Modifier.height(24.dp))
                CustomButton("로그인", modifier = Modifier.fillMaxWidth()) {
                    onLogin()
                }
                Spacer(modifier = Modifier.height(12.dp))
                CustomButton(
                    "취소",
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color(0xffffffff),
                    border = BorderStroke(1.dp, Color(0xff303030)),
                    textColor = Color(0xff000000),
                ) { onDismiss() }
            }
        }
    }
}