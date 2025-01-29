package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.model.LargeCategory

@Composable
fun CategorySelector(
    selectedCategorys: List<LargeCategory>,
    onChange: (LargeCategory) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column {
        Text(
            text = "나만의 큐레이션",
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val categories = LargeCategory.entries

            val categoryShape = RoundedCornerShape(50.dp)

            categories.forEachIndexed { index, category ->
                val isSelected = selectedCategorys.any { it == category }

                // todo: 카테고리 칩을 컴포넌트로 따로 빼서 boolean 파라미터 받는거로 변경하기
                Surface(
                    onClick = { onChange(category) },
                    shape = categoryShape,
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) Color(0xff303030) else Color(0xffC6C6C6)
                    ),
                    color = if (isSelected) Color(0xff303030) else Color(0xffFFFFFF)
                ) {
                    Text(
                        text = category.nameKo,
                        color = if (isSelected) Color(0xffffffff) else Color(0xff5E5E5E),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}