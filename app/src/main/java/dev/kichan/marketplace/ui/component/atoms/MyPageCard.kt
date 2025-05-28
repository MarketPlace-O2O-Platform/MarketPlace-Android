package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.marketplace.ui.data.CouponBoxProps

@Composable
fun MyPageCard(couponBoxProps: CouponBoxProps, modifier: Modifier = Modifier) {
    var isBookmarked by remember { mutableStateOf(false) }

//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        verticalAlignment = Alignment.Top
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.cham),
//            contentDescription = null,
//            modifier = Modifier
//                .size(100.dp)
//                .clip(RoundedCornerShape(8.dp)),
//            contentScale = ContentScale.Crop
//        )
//
//        Spacer(modifier = Modifier.width(16.dp))
//
//        Column(modifier = Modifier.weight(1f)) {
//            Text(
//                text = event.name,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold,
//                maxLines = 1
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = event.name,
//                fontSize = 14.sp,
//                color = Color.Gray,
//                maxLines = 2
//            )
//            Spacer(modifier = Modifier.height(20.dp))
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        imageVector = Icons.Filled.LocationOn,
//                        contentDescription = "Location",
//                        tint = Color.Gray,
//                        modifier = Modifier.size(16.dp)
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = event.address,
//                        fontSize = 12.sp,
//                        color = Color.Gray
//                    )
//                }
//
//                Icon(
//                    imageVector = if (!isBookmarked) Carbon_bookmark else Icons.Default.Favorite,
//                    contentDescription = "Bookmark",
//                    tint = Color.Gray,
//                    modifier = Modifier
//                        .size(24.dp)
//                        .clickable { isBookmarked = !isBookmarked }
//                )
//            }
//        }
//    }
}

@Preview(showBackground = true)
@Composable
fun MyPageCardPreview() {
//    MyPageCard(
//        event = Market(
//            id = 6576,
//            name = "Joan Potts",
//            description = "blandit",
//            operationHours = "assueverit",
//            closedDays = "inani",
//            phoneNumber = "(929) 451-1388",
//            address = "cetero",
//            thumbnail = "postea"
//        )
//    )
}
