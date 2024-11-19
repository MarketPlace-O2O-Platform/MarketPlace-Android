package dev.kichan.marketplace.ui.page

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat.startActivityForResult
import coil3.compose.rememberAsyncImagePainter
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.CouponCreateReq
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository.CouponRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.common.toUsFormat
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketCreateReq
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository.MarketRepositoryImpl
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun ApiTestPage() {
    val repository = MarketRepositoryImpl()
    val image = remember{ mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        image.value = it
    }

    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Button(onClick = {
                galleryLauncher.launch("image/*")
            }) {
                Text(text = "이미지 가져 오기")
            }
            Button(onClick = { 
//                CoroutineScope(Dispatchers.IO).launch {
//                    repository.createMarket(
//                        body = MarketCreateReq()
//                    )
//                }
            }) {
                Text(text = "클릭")
            }

            if(image.value != null) {
                Image(painter = rememberAsyncImagePainter(image.value), contentDescription = null)
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

