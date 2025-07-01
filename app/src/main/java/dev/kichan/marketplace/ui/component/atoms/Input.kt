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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
fun Input(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontSize = 13.sp),
    placeholder: String? = null,
    icon: ImageVector? = null,
    isSuccess: Boolean = false,
    isError: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    inputType: InputType = InputType.Text,
    readOnly: Boolean = false,
) {
    val shape = RoundedCornerShape(2.dp)
    val contentColor = Gray_9
    val backgroundColor = Color(0xFFFFFFFF)
    val isContentShow = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = value,
            onValueChange = onChange,
            modifier = Modifier,
            textStyle = textStyle.copy(color = contentColor),
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            readOnly = readOnly,
        ) { innerTextField ->
            Row(
                Modifier
                    .background(color = backgroundColor, shape = shape)
                    .border(1.dp, Gray_3, shape)
                    .padding(horizontal = 16.dp, vertical = 13.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (value.isBlank() && placeholder != null) {
                        Text(
                            text = placeholder,
                            style = textStyle.copy(
                                color = Gray_6
                            )
                        )
                    }

                    if (inputType == InputType.Text || isContentShow.value) {
                        innerTextField()
                    } else {
                        Text(text = "*".repeat(value.length), color = contentColor)
                    }

                    if (inputType == InputType.Password) {
                        Icon(
                            painter = if (isContentShow.value) {
                                painterResource(id = R.drawable.ic_visibility)
                            } else {
                                painterResource(id = R.drawable.ic_visibility_off)
                            },
                            contentDescription = "Toggle Password Visibility",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .clickable { isContentShow.value = !isContentShow.value }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputPreview() {
    val input = remember { mutableStateOf("") }

    MarketPlaceTheme {
        Input(
            input.value,
            { input.value = it },
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            placeholder = "이름을 입력하세요",
            icon = Icons.Default.Search,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputSuccessPreview() {
    val input = remember { mutableStateOf("") }

    MarketPlaceTheme {
        Input(
            input.value,
            { input.value = it },
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            placeholder = "비밀번호를 입력하세요",
            icon = Icons.Default.Search,
            isSuccess = true,
            isError = true,
            inputType = InputType.Password
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputErrorPreview() {
    val input = remember { mutableStateOf("") }

    MarketPlaceTheme {
        Input(
            input.value,
            { input.value = it },
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            isError = true,
            icon = Icons.Default.Search,
            placeholder = "이름을 입력하세요"
        )
    }
}

enum class InputType {
    Text, Password
}