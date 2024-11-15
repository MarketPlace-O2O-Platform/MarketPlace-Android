package dev.kichan.marketplace.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.CouponCreateReq
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository.CouponRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.common.toUsFormat
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun ApiTestPage() {
    val repository = CouponRepositoryImpl()
    
    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Button(onClick = { 
                
                CoroutineScope(Dispatchers.IO).launch { 
                    val res = repository.createCoupon(
                        body = CouponCreateReq(
                            name = "인천대학교 학비 90%할인 쿠폰",
                            description = "아 이게 장학금인가",
                            deadline = LocalDateTime.of(2024, 10, 31, 23, 59, 59).toUsFormat(),
                            stock = 0,
                        ),
                        marketId = 1
                    )

                    if(res.isSuccessful) {
                        Log.d("CouponCreate", "성공 했 ${res.body()}")
                    }
                }
            }) {
                Text(text = "클릭")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApiTestPagePreview() {
    MarketPlaceTheme {
        ApiTestPage()
    }
}

