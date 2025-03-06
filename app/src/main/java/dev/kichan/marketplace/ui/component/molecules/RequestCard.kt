package dev.kichan.marketplace.ui.component.molecules

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.kichan.marketplace.ui.component.atoms.Button
import dev.kichan.marketplace.ui.theme.PretendardFamily
import java.time.LocalDate

@Composable
fun RequestCard(
    //todo: 나중에 더 좋은 이름으로 변경
    modifier: Modifier = Modifier,
    marketName : String,
    likeCount : Int,
    thumbnail: String,
    isMyDone : Boolean,
    isRequestDone: Boolean,
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = "Banner Image",
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Text(
                text = "‘${marketName}’ 할인을 받고 싶어요!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = PretendardFamily,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (isRequestDone) {
                        Text(text = "공감 마감", fontSize = 12.sp, color = Color.Gray)
                        Text(
                            text = "제휴 컨텍 중",
                            fontSize = 12.sp,
                            color = Color(0xff383838),
                            fontWeight = FontWeight.Medium
                        )
                    } else {
                        val current = LocalDate.now()
                        Text(text = "마감까지 None일 남음", fontSize = 12.sp, color = Color.Gray)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = likeCount.toString())
                    Spacer(modifier = Modifier.width(4.dp))
                    //todo: 아이콘 변경
                    Icon(Icons.Filled.FavoriteBorder, contentDescription = "Like")
                }
            }

            Spacer(modifier = Modifier.height(7.dp))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xffF3F0F0))
            )

            Spacer(modifier = Modifier.height(8.dp))

            val buttonModifier = Modifier.fillMaxWidth()

            if (isRequestDone) {
                Button(text = "제휴 컨텍중", isDisable = true, modifier = buttonModifier) {

                }
            } else {
                Button(
                    text = "공감 하기",
                    icon = Icons.Default.FavoriteBorder,
                    modifier = buttonModifier
                ) {

                }
            }
        }
    }
}