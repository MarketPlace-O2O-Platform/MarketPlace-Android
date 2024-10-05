package dev.kichan.marketplace.ui.component

import Carbon_bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.marketplace.R

@Composable
fun CurationCard(imageResId: Int, marketName: String, location: String) {
    var isBookMark by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(192.dp)
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1.0f)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Icon(
                imageVector = if (!isBookMark) Carbon_bookmark else Icons.Default.Favorite, // Scrap bookmark icon
                contentDescription = "Bookmark",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(24.dp)
                    .clickable { isBookMark = !isBookMark }
            )
        }

        // Market name text
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = marketName,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        // Location icon and text
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn, // Location icon
                contentDescription = "Location",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = location,  // Location text
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCurationCard() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listOf(R.drawable.cafe, R.drawable.roomex)) { imageResId ->
            CurationCard(imageResId = imageResId, marketName = "콜드케이스 인하대점", location = "송도")
        }
    }
}
