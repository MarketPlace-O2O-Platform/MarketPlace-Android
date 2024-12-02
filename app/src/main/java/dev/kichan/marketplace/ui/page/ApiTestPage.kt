package dev.kichan.marketplace.ui.page

import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.rememberAsyncImagePainter
import dev.kichan.marketplace.model.data.login.LoginRes
import dev.kichan.marketplace.model.repository.MemberRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository.CouponUserRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository.FavoriteRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository.MarketOwnerRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository.MemberCouponRepositoryImpl
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ApiTestPage() {
    val context = LocalContext.current
    val repository = MarketOwnerRepositoryImpl()
    val memberRepo = MemberRepositoryImpl()
    val favotriteRepo = FavoriteRepositoryImpl()
    val CouponRepo = CouponUserRepositoryImpl()
    val memberCouponRepo = MemberCouponRepositoryImpl()


    val image = remember { mutableStateOf<Uri?>(null) }
    val isLoading = remember { mutableStateOf(false) }
    var res = remember {
        mutableStateOf<LoginRes?>(null)
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            image.value = it
        }

    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    memberCouponRepo.getValidMemberCoupons(202401598)
                }
            }) {
                Text(text = "클릭")
            }

            if (isLoading.value) {
                Text(text = "로딩중")
            }

            if (image.value != null) {
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

