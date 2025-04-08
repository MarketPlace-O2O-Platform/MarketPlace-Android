package dev.kichan.marketplace.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import dev.kichan.marketplace.model.data.like.TempMarketRes

@Composable
fun TempMarketSearchLiseItem(
    market: TempMarketRes,
    onCheerClick: (marketId: Long, newIsCheer: Boolean) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 썸네일
            Image(
                painter = rememberAsyncImagePainter(market.thumbnail),
                contentDescription = market.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(76.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 제목 + 설명
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = market.name,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = market.description.orEmpty(),
                    style = MaterialTheme.typography.body2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // 공감 버튼
            var isCheer by remember { mutableStateOf(market.isCheer) }
            val cheerCount = remember { mutableStateOf(market.cheerCount) }

            Row(
                modifier = Modifier
                    .clickable {
                        val newState = !isCheer
                        isCheer = newState
                        // 로직: 서버에 호출 등
                        onCheerClick(market.id, newState)
                        // 카운트 증감
                        cheerCount.value = if (newState) cheerCount.value + 1 else cheerCount.value - 1
                    }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    contentDescription = if (isCheer) "공감 취소" else "공감하기",
                    tint = if (isCheer) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isCheer) "공감 ${cheerCount.value}" else "공감하기",
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}
