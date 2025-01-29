package dev.kichan.marketplace.ui.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.marketplace.CouponViewModel
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun ApiTestPage(couponViewModel: CouponViewModel) {
    var search by remember { mutableStateOf("") }
}

@Preview(showBackground = true)
@Composable
fun ApiTestPagePreview() {
    MarketPlaceTheme {
        ApiTestPage(CouponViewModel())
    }
}

