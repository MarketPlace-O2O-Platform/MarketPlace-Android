package dev.kichan.marketplace.ui.page

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.network.NetworkRequestBody
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.market.MarketCreateReq
import dev.kichan.marketplace.model.getAuthToken
import dev.kichan.marketplace.model.repository.MarketOwnerRepository
import dev.kichan.marketplace.ui.component.atoms.Button
import dev.kichan.marketplace.ui.faker
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

@Composable
fun ApiTestPage() {
    val context = LocalContext.current
    val token = getAuthToken(context).collectAsState(null)

    val selectedImageUris = remember { mutableStateListOf<Uri>() }
    var isLoading by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris ->
            selectedImageUris.clear()
            selectedImageUris.addAll(uris)
        }
    )

    fun uriToMultipart(context: Context, uri: Uri): MultipartBody.Part? {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.cacheDir, "selected_image_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData("images", file.name, requestBody)
    }


    val onDummyMarketData = {
        val repo = MarketOwnerRepository()

        val imageParts = selectedImageUris.mapNotNull { uri ->
            uriToMultipart(context, uri)
        }

        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 1..30) {
                val market = MarketCreateReq(
                    name = faker.company().name(),
                    description = faker.lorem().paragraph(1),
                    operationHours = "121",
                    closedDays = "12",
                    phoneNumber = faker.phoneNumber().phoneNumber(),
                    major = LargeCategory.entries.random().backendLable,
                    address = "인천광역시 연수구"
                )
                val res = repo.createMarket(market, imageParts)
                if(res.isSuccessful) {
                    Log.i("dummy", "성공 $i")
                }
                else {
                    Log.e("dummy", "실패(${res.code()}) $i")
                }
                if(i % 5 == 0) {
                    delay(300)
                }
            }
            withContext(Dispatchers.Main) {
                isLoading = false
            }
        }
    }

    val setToken = {
        if (!token.value.isNullOrEmpty()) {
            NetworkModule.updateToken(token.value)
            Log.i("authToken", token.value.toString())
        }
    }

    setToken()

    Scaffold { _ ->
        Column(modifier = Modifier.padding(16.dp)) {
            if (isLoading) {
                Text("로딩중")
            }

            Button("이미지 선택하기") { imagePickerLauncher.launch("image/*") }

            Spacer(modifier = Modifier.height(16.dp))

            Button("매장 더미데이터 생성") { onDummyMarketData() }

            selectedImageUris.forEach { uri ->
                Text(text = "선택된 이미지: $uri")
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

