package dev.kichan.marketplace.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip

@Composable
fun DayOfWeekSelector(_day : Int, onChange : (Int) -> Unit) {
    // _day는 요일을 전달받는 파라미터 / 0: 월, 1: 화, 2: 수, ...

    val days = listOf("월", "화", "수", "목", "금", "토", "일")
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        days.forEachIndexed { index, day ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (_day == index) Color.Black else Color.Transparent)
                    .clickable { onChange(index) }
            ) {
                Text(
                    text = day,
                    fontSize = 14.sp,
                    color = if (_day == index) Color.White else Color.Gray,
                    fontWeight = if (_day == index) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDayOfWeekSelector() {
    DayOfWeekSelector(0, {})
}