package dev.kichan.marketplace.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.marketplace.ui.theme.Blue_4
import dev.kichan.marketplace.ui.theme.Gray_0
import dev.kichan.marketplace.ui.theme.Gray_3
import dev.kichan.marketplace.ui.theme.Gray_6
import dev.kichan.marketplace.ui.theme.Gray_9
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun Input(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    placeholder: String? = null,
    icon: ImageVector? = null,
    isSuccess: Boolean = false,
    isError: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    inputType: InputType = InputType.Text,
) {
    val shape = RoundedCornerShape(2.dp)
    val contentColor = Gray_9
    val backgroundColor = Gray_0
    val isContentShow = remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        textStyle = textStyle.copy(color = contentColor),
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
    ) { innerTextField ->
        Row(
            modifier
                .background(color = backgroundColor, shape = shape)
                .border(1.dp, Gray_3, shape)
                .padding(horizontal = 16.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.fillMaxWidth()
            ) {
                if (value.isBlank() && placeholder != null) {
                    // 입력된게 없으면 Placeholder를 보여줌
                    Text(
                        text = placeholder,
                        style = textStyle.copy(
                            color = Gray_6
                        )
                    )
                }

                if(inputType == InputType.Text || isContentShow.value) {
                    innerTextField()
                }
                else {
                    Text(text = "*".repeat(value.length), color = contentColor)
                }

//                if (inputType == InputType.Password) {
//                    Image(
//                        contentDescription = null,
//                        modifier = Modifier
//                            .align(Alignment.CenterEnd)
//                            .clickable {
//                                isContentShow.value = !isContentShow.value
//                            },
//                        colorFilter = ColorFilter.tint(contentColor.value)
//                    )
//                }
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