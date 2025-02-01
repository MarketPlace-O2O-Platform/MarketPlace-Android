package dev.kichan.marketplace.ui.page

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.marketplace.CouponViewModel
import dev.kichan.marketplace.MarketViewModel
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun ApiTestPage(marketViewModel: MarketViewModel) {
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
//        marketViewModel.
    }
}

@Preview(showBackground = true)
@Composable
fun ApiTestPagePreview() {
    MarketPlaceTheme {
        ApiTestPage(MarketViewModel())
    }
}

