package dev.kichan.marketplace.ui.component.atoms


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.theme.Gray_3
import dev.kichan.marketplace.ui.theme.Gray_6
import dev.kichan.marketplace.ui.theme.Gray_9
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun PasswordInput(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    textStyle: TextStyle = TextStyle(fontSize = 13.sp),
) {
    val shape = RoundedCornerShape(2.dp)
    val contentColor = Gray_9
    val backgroundColor = Color(0xFFFFFFFF)
    val isContentVisible = remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        textStyle = textStyle.copy(color = contentColor),
        singleLine = true
    ) { innerTextField ->
        Row(
            modifier
                .background(color = backgroundColor, shape = shape)
                .border(1.dp, Gray_3, shape)
                .padding(horizontal = 16.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.weight(1f) // 텍스트 필드가 Row 안에서 확장되도록 설정
            ) {
                if (value.isBlank() && placeholder != null) {
                    Text(
                        text = placeholder,
                        style = textStyle.copy(color = Gray_6)
                    )
                }

                if (isContentVisible.value) {
                    innerTextField()
                } else {
                    Text(text = "*".repeat(value.length), color = contentColor)
                }
            }

            Icon(
                painter = if (isContentVisible.value) {
                    painterResource(id = R.drawable.ic_visibility)
                } else {
                    painterResource(id = R.drawable.ic_visibility_off)
                },
                contentDescription = "Toggle Password Visibility",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable { isContentVisible.value = !isContentVisible.value }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordInputPreview() {
    val input = remember { mutableStateOf("") }

    MarketPlaceTheme {
        PasswordInput(
            value = input.value,
            onChange = { input.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            placeholder = "비밀번호를 입력하세요"
        )
    }
}
