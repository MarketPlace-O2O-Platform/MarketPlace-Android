package dev.kichan.marketplace.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TwoButtonGroup(
    labels: List<Pair<String, () -> Unit>>,
    modifier: Modifier = Modifier,
    round : Dp,
) {
    val border = BorderStroke(1.dp, Color(0xFFDDDDDD))
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(round),
        border = border,
        color = Color.White,
    ) {
        Row {
            @Composable
            fun btn(
                shape: RoundedCornerShape,
                text: String,
                onClick: () -> Unit,
            ) = Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(shape)
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xff545454),
                    maxLines = 1
                )
            }

            Row(
                modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                btn(
                    shape = RoundedCornerShape(topStart = round, bottomStart = round),
                    text = labels[0].first,
                    onClick = labels[0].second
                )

                Divider(
                    color = Color(0xFFCCCCCC),
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(1.dp)
                        .height(18.dp)
                )

                btn(
                    shape = RoundedCornerShape(topEnd = round, bottomEnd = round),
                    text = labels[1].first,
                    onClick = labels[1].second
                )
            }
        }
    }
}