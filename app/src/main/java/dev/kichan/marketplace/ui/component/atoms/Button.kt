package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
fun Button(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    backgroundColor: Color = Gray_8,
    shape: Shape = RoundedCornerShape(4.dp),
    border: BorderStroke? = null,
    isDisable: Boolean = false,
    icon: ImageVector? = null,
    contentPadding: PaddingValues = PaddingValues(vertical = 12.dp, horizontal = 12.dp),
    onClick: () -> Unit,
) {
    val bgc = animateColorAsState(
        targetValue = if (!isDisable) {
            backgroundColor
        } else {
            Color(0xFFBDBDBD)
        }
    )

    Row(
        modifier
            .let { if (border != null) it.border(border, shape) else it }
            .background(
                color = bgc.value, shape = shape
            )
            .clickable(!isDisable) { onClick() }
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null, tint = textColor, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = text,
            style = TextStyle(
                color = textColor,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(700),
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
            icon = Icons.Default.Home
        )
    }
}