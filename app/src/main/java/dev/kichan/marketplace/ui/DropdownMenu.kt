package dev.kichan.marketplace.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.ui.component.atoms.Input
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    value : String,
    options : List<String>,
    placeholder: String? = null,
    onChange: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }, // 드롭다운 상태 토글
    ) {
        Input(
            value = value,
            onChange = {},
            placeholder = placeholder,
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            textStyle = TextStyle( // 수정됨: 텍스트 스타일 정의
                fontSize = 13.sp,
                lineHeight = 20.8.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(400),
                color = Color(0xFF838A94)
            ),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color(0xffffffff))
                .border(1.dp, Color(0xffE0E0E0))
        ) {
            options.forEachIndexed { index, school ->
                CustomDropdownMenuItem(
                    text = school,
                    isLastItem = index == options.size - 1
                ) {
                    onChange(school)
                    expanded = false
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DropDownMenuPreview() {
    MarketPlaceTheme {
        DropDownMenu(
            "인천대학교",
            listOf("인천대학교", "연세대학교", "인하대학교"),
        ) {

        }
    }
}