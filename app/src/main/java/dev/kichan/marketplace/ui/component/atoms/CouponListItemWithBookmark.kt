package dev.kichan.marketplace.ui.component.atoms

import Carbon_bookmark
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.kichan.marketplace.model.repository.FavoritesRepository
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CouponListItemWithBookmark(
    modifier: Modifier = Modifier,
    title: String,
    couponDescription: String,
    location: String,
    likes: Int,
    category: String,
    thumbnail: String,
) {
    val repository = FavoritesRepository()

    val onFavoriteClick: () -> Unit = {
//        CoroutineScope(Dispatchers.IO).launch {
//            repository.favorites()
//        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = "Event Thumnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(110.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // 텍스트 섹션
        Column(
            modifier = Modifier
                .height(110.dp)
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    fontFamily = PretendardFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = couponDescription,
                    fontFamily = PretendardFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xff7d7d7d),
                    maxLines = 2,
                    lineHeight = 18.sp,
                )
            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                // 위치
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Place,
                        contentDescription = "위치",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = location,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                    )
                }

                IconButton(
                    onClick = { onFavoriteClick() }
                ) {
                    Icon(imageVector = Carbon_bookmark, contentDescription = null)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventCardPreview() {
    MarketPlaceTheme {
        CouponListItemWithBookmark(
            title = "참피온삼겹살 트리플 스트리트점",
            couponDescription = "방탈출 카페 2인 이용권\n스머프와 함께 즐기는 미디어아트 보드게임!",
            location = "송도",
            likes = 440,
            category = "음식&주점",
            modifier = Modifier,
            thumbnail = "",
        )
    }
}