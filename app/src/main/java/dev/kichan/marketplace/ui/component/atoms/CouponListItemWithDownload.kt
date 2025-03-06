package dev.kichan.marketplace.ui.component.atoms

import Carbon_bookmark
import Download
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
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun CouponListItemWithDownload(
    imageRes: Int,
    title: String,
    couponDescription: String,
    location: String,
    likes: Int,
    category: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(110.dp)
                .clip(RoundedCornerShape(4.dp))
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
                    onClick = {}
                ) {
                    //todo: 아 몰라 나중에 해 아이콘 변경
                    Icon(imageVector = Download, contentDescription = null)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CouponListItemWithDownloadPreview() {
    MarketPlaceTheme {
        CouponListItemWithDownload(
            imageRes = R.drawable.desert,
            title = "참피온삼겹살 트리플 스트리트점",
            couponDescription = "방탈출 카페 2인 이용권\n스머프와 함께 즐기는 미디어아트 보드게임!",
            location = "송도",
            likes = 440,
            category = "음식&주점",
            modifier = Modifier
        )
    }
}