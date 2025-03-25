package dev.kichan.marketplace.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.data.coupon.Coupon
import dev.kichan.marketplace.model.repository.CouponRepository
import dev.kichan.marketplace.ui.component.atoms.CouponListItem
import dev.kichan.marketplace.ui.component.atoms.CouponListItemProps
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CouponListPage(
    navController: NavHostController = rememberNavController(),
    type: String,
) {
    val repo = CouponRepository()
    val title = if(type == "popular") "인기 쿠폰" else "x월 신규" //todo: 월 추가
    var couponList by remember { mutableStateOf<List<CouponListItemProps>>(listOf()) }

    val onLoadCoupon = {
        CoroutineScope(Dispatchers.IO).launch {
            val _couponList: List<CouponListItemProps> = if (type == "popular") {
                val res = repo.getPopularCoupon(null, 20)
                if (!res.isSuccessful) {
                    throw Exception("실패!")
                }
                res.body()!!
                    .response.couponResDtos.map {
                        it.toCouponListItemProps()
                    }
            } else {
                val res = repo.getLatestCoupon(null, null, 20)
                if (!res.isSuccessful) {
                    throw Exception("실패!")
                }
                res.body()!!
                    .response.couponResDtos.map {
                        it.toCouponListItemProps()
                    }
            }

            withContext(Dispatchers.Main) {
                couponList += _couponList
            }
        }
    }

    LaunchedEffect(Unit) {
        onLoadCoupon()
    }

    Scaffold(
        topBar = {
            NavAppBar("$title | 멤버십 혜택") { navController.popBackStack() }
        }
    ) {
        LazyColumn (
            modifier = Modifier.padding(it)
        ) {
            items(couponList) {
                CouponListItem(
                    modifier = Modifier.clickable { navController.navigate("${Page.EventDetail.name}/${it.marketId}") },
                    props = it
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CouponListPagePreview() {
    MarketPlaceTheme() {
        CouponListPage(type = "popular")
    }
}