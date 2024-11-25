package dev.kichan.marketplace.ui.page

import LargeCategory
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.rememberAsyncImagePainter
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import dev.kichan.marketplace.model.repository.MemberRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketCreateReq
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository.FavoriteRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository.MarketRepositoryImpl
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ApiTestPage() {
    val context = LocalContext.current
    val repository = MarketRepositoryImpl()
    val memberRepo = MemberRepositoryImpl()
    val favotriteRepo = FavoriteRepositoryImpl()


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
                galleryLauncher.launch("image/*")
            }) {
                Text(text = "갤러리")
            }

            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val re = repository.createMarket(
                        context = context,
                        body = MarketCreateReq(
                            name = "공공공공씨네 주먹밥",
                            description = "특징) 이거 먹을바에 봉구스가지 말 나옴",
                            operationHours = "22",
                            closedDays = "월화수",
                            phoneNumber = "112",
                            major = LargeCategory.Food.backendLable,
                            address = "ds"
                        ), image = image.value!!
                    )
                }


            }) {
                Text(text = "마켓")
            }
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val r = favotriteRepo.favoriteToggle("202401598", "1")

                    if (r.isSuccessful) {
                        Log.d("TAG", "성공")
                    } else {
                        Log.d("TAG", "실패 ${r.message()}")
                    }
                }
            }) {
                Text(text = "찜")
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

