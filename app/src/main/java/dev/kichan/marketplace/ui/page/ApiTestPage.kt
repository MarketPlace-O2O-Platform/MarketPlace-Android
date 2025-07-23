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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.Member.SaveAccountReq
import dev.kichan.marketplace.model.data.coupon.CouponCreateReq
import dev.kichan.marketplace.model.data.market.MarketCreateReq
import dev.kichan.marketplace.model.getAuthToken
import dev.kichan.marketplace.model.repository.AuthRepositoryImpl
import dev.kichan.marketplace.model.repository.CouponOwnerRepository
import dev.kichan.marketplace.model.repository.MarketOwnerRepository
import dev.kichan.marketplace.model.repository.PayBackCouponMemberRepository
import dev.kichan.marketplace.model.service.PayBackCouponMember
import dev.kichan.marketplace.ui.component.atoms.CustomButton
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
    val authRepository = AuthRepositoryImpl(context)

    val selectedImageUris = remember { mutableStateListOf<Uri>() }
    var isLoading by remember { mutableStateOf(false) }
    var inputAccount by remember { mutableStateOf("") }
    var inputAccountNumber by remember { mutableStateOf("") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris ->
            selectedImageUris.clear()
            selectedImageUris.addAll(uris)
        }
    )
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

            TextField(
                value = inputAccount,
                onValueChange = { inputAccount = it },
                placeholder = { Text("은행명") })
            TextField(
                value = inputAccountNumber,
                onValueChange = { inputAccountNumber = it },
                placeholder = { Text("계좌번호") })
            Button(
                onClick = {
                    val req = SaveAccountReq(
                        account = inputAccount,
                        accountNumber = inputAccountNumber
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        authRepository.saveAccountPermit(req)
                    }
                }
            ) {
                Text("계좌 등록")
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

