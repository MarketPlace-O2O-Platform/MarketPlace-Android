package dev.kichan.marketplace.ui.component.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.ui.component.atoms.CustomButton
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun TempMarketCard(
    marketName: String,
    thumbnail: String,
    cheerCount: Int,
    isCheer: Boolean,
    dueDate: Int,
    onCheer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f),
            model = NetworkModule.getImageModel(LocalContext.current, thumbnail),
            contentDescription = "Banner Image",
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 4.dp)) {
            Text(
                text = "'${marketName}' 할인을 받고 싶어요!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = PretendardFamily,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 상태 텍스트 + 하트
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "마감까지 ${dueDate}일 남음",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = cheerCount.toString(), fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(R.drawable.fill_heart),
                        contentDescription = "Heart",
                        tint = Color.Unspecified
                    )
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

            if (isCheer) {
                CustomButton(
                    text = "공감 완료",
                    isDisable = true,
                    contentPadding = PaddingValues(vertical = 6.dp, horizontal = 12.dp),
                    modifier = buttonModifier
                ) { }
            } else {
                CustomButton(
                    text = "공감하기",
                    icon = painterResource(R.drawable.empty_heart),
                    contentPadding = PaddingValues(vertical = 6.dp, horizontal = 12.dp),
                    modifier = buttonModifier
                ) {
                    onCheer()
                }
            }
        }
    }
}
