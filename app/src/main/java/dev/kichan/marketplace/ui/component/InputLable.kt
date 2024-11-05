package dev.kichan.marketplace.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.ui.theme.Gray_8
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun InputLabel(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    placeholder: String? = null,
    icon: ImageVector? = null,
    isSuccess: Boolean = false,
    error: String? = null,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    inputType: InputType = InputType.Text
) {
    Column(modifier) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = label,
                style = TextStyle(
                    color = Gray_8,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Input(
            value = value,
            onChange = onChange,
            modifier = Modifier,
            textStyle = textStyle,
            placeholder = placeholder,
            icon = icon,
            isSuccess = isSuccess,
            isError = error != null,
            singleLine = false,
            maxLines = maxLines,
            minLines = minLines,
            inputType = inputType
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputLabelPreview() {
    MarketPlaceTheme {
        InputLabel(
            value = "",
            onChange = {},
            label = "이름",
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            placeholder = "이름을 입력하세요.",
            icon = Icons.Default.Person
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputLabelErrorPreview() {
    MarketPlaceTheme {
        val input = remember { mutableStateOf("") }

        InputLabel(
            value = input.value,
            onChange = { input.value = it },
            label = "이름",
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            placeholder = "이름을 입력하세요.",
            icon = Icons.Default.Person,
            error = "이미 사용중인 아이디입니다."
        )
    }
}