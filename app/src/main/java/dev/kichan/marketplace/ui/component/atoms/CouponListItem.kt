package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.faker
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

data class CouponListItemProps(
    val id: Long,
    val name: String,
    val marketName: String,
    val marketId: Long,
    val imageUrl: String,
    val address: String,
    val isAvailable: Boolean,
    val isMemberIssued: Boolean,
    val couponType : String,
)

@Composable
fun CouponListItem(
    modifier: Modifier = Modifier,
    props: CouponListItemProps,
    onDownloadClick: (Long) -> Unit
) {
    with(props) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = PAGE_HORIZONTAL_PADDING)
        ) {
            AsyncImage(
                model = NetworkModule.getImageModel(LocalContext.current, imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .height(110.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = marketName,
                        fontFamily = PretendardFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xff4b4b4b),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = name,
                        fontFamily = PretendardFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff4b4b4b),
                        maxLines = 1,
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
                            text = address,
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                        )
                    }
                    IconButton(
                        onClick = { onDownloadClick(id) },
                        enabled = !isMemberIssued
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_download),
                            tint = if(isMemberIssued) Color(0xFFA5A5A5) else Color(0xFF4B4B4B),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CouponListItemWithDownloadPreview() {
    MarketPlaceTheme {
        CouponListItem(
            props = CouponListItemProps(
                name = faker.name().title(),
                marketName = faker.company().name(),
                imageUrl = faker.company().logo(),
                address = faker.address().fullAddress(),
                isAvailable = false,
                marketId = 1L,
                id = 1L,
                isMemberIssued = false,
                couponType = "PAYBACK"
            ),
            onDownloadClick = {}
        )
    }
}