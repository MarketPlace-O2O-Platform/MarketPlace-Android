package dev.kichan.marketplace.ui.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.kakao.vectormap.MapView
import dev.kichan.marketplace.ui.component.KakaoMap
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun MapPage() {
    KakaoMap(latitude = 0.0f, longitude = 0.0f)
}

@Preview(showBackground = true)
@Composable
fun MapPagePreview() {
    MarketPlaceTheme {
        MapPage()
    }
}