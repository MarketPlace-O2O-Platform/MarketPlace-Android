package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.ui.theme.Gray_8
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun Button(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    backgroundColor: Color = Gray_8,
    shape: Shape = RoundedCornerShape(4.dp),
    border: BorderStroke? = null,
    isDisable: Boolean = false,
) {
    val bgc = animateColorAsState(
        targetValue = if (!isDisable) {
            backgroundColor
        } else {
            Color(0xFFBDBDBD)
        }
    )

    Column(
        modifier
            .clickable(!isDisable) { onClick() }
            .let { if (border != null) it.border(border, shape) else it }
            .background(
                color = bgc.value, shape = shape
            )
            .padding(horizontal = 12.dp, vertical = 15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = textColor,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
            )
        )
    }
}

@Preview
@Composable
fun InuButtonPreview() {
    MarketPlaceTheme {
        Button(
            modifier = Modifier.fillMaxWidth(),
            text = "버튼",
            onClick = {},
        )
    }
}