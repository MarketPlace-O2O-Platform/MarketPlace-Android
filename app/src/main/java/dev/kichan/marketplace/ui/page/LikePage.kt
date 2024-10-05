package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.component.CurationCard
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun LikePage(navController: NavController) {
    var searchInput by remember { mutableStateOf("") }

    LazyColumn {
        item {
            // todo: TextField 디자인 수정
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .padding(
                        top = 12.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 40.dp
                    )
            ) {
                TextField(
                    value = searchInput,
                    onValueChange = { searchInput = it },
                    placeholder = {
                        Text(
                            text = "제휴 할인받고 싶은 매장을 알려주세요",
                            color = Color(0xffBDB6B6),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Thin
                        )
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PAGE_HORIZONTAL_PADDING, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        // todo: 아이콘 변경
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = Color(0xff108CFF)
                    )
                    Spacer(modifier = Modifier.width(11.dp))
                    Text(text = "내 공감권", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(11.dp))
                    Text(text = "1개", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Text(
                    text = "공감권은 매일 자정에 충전됩니다.",
                    color = Color(0xffBDB6B6),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = PretendardFamily
                )
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xffF3F0F0))
            )
        }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_PADDING),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(10) {
                    CurationCard(
                        modifier = Modifier.width(284.dp),
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 제목 텍스트
                Text(
                    text = "지금 공감하면 할인권을 드려요",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.width(11.dp))

                Surface(
                    color = Color(0xFF2196F3),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Text(
                        text = "EVENT",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }

        item {
            Column {
                for (i in 1..10) {
                    Row {
                        CurationCard(modifier = Modifier.weight(1.0f))
                        Spacer(modifier = Modifier.width(4.dp))
                        CurationCard(modifier = Modifier.weight(1.0f))
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LikePagePreview() {
    MarketPlaceTheme {
        LikePage(rememberNavController())
    }
}