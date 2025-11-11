package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun SearchResultItem(title: String, description: String, imageUrl:String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        AsyncImage(
            model = NetworkModule.getImageModel(LocalContext.current, imageUrl),
            contentDescription = null,
            modifier = Modifier
                .width(110.dp)
                .height(110.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(600),
                color = Color(0xFF333333),
                modifier = Modifier.padding(start = 16.dp, bottom = 4.dp).fillMaxWidth()
            )
            Text(
                text = description,
                fontSize = 13.sp,
                lineHeight = 18.2.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(500),
                color = Color(0xFF7D7D7D),
                modifier = Modifier.padding(start = 16.dp).fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(start = 16.dp, top = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location),
                    contentDescription = "Location",
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "송도",
                    fontSize = 13.sp,
                    lineHeight = 22.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF5E5E5E),
                )
            }
        }
    }
}