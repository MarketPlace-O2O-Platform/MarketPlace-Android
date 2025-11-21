package dev.kichan.marketplace.ui.page

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.component.atoms.CustomButton
import dev.kichan.marketplace.ui.component.atoms.Input
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.ui.theme.PretendardFamily
import dev.kichan.marketplace.ui.viewmodel.MyPage2ViewModel
import dev.kichan.marketplace.viewmodel.ReceiptUploadViewModel

@Composable
fun ReceiptUploadPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ReceiptUploadViewModel = viewModel(),
    couponId: Long,
) {
    val context = LocalContext.current
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    // Activity 스코프의 MyPage2ViewModel을 가져와서 영수증 업로드 시 갱신
    val myPageViewModel: MyPage2ViewModel? = (context as? ComponentActivity)?.let {
        viewModel(viewModelStoreOwner = it)
    }

    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        viewModel.setImage(uri)
    }

    // 페이지 열릴 때 저장된 계좌 정보 불러오기
    LaunchedEffect(Unit) {
        viewModel.loadSavedAccount()
    }

    Scaffold(
        topBar = {
            NavAppBar("환급하기") { navController.popBackStack() }
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(20.dp)
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            Text(
                "환급 받을 영수증을\n" + "등록해주세요",
                fontSize = 24.sp,
                fontWeight = FontWeight(800),
                fontFamily = PretendardFamily
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color(0xffE0E0E0))
                    .clickable {
                        picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                contentAlignment = Alignment.Center
            ) {
                if (uiState.imageUri != null) {
                    AsyncImage(
                        model = uiState.imageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )
                } else {
                    Column {
                        Icon(
                            painter = painterResource(R.drawable.ic_recepit_add),
                            contentDescription = null,
                            tint = Color(0xffE0E0E0),
                            modifier = Modifier
                                .size(64.dp)
                        )
                        Text(
                            "24시간 내로 환급이 이루어지지 않을 시\n고객센터(쿠러미 카카오채널)로 문의주세요!",
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                            fontFamily = PretendardFamily
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "환급 받을 계좌를\n" + "입력해주세요",
                fontSize = 24.sp,
                fontWeight = FontWeight(800),
                fontFamily = PretendardFamily
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Input(
                    value = uiState.bankName,
                    onChange = { viewModel.setBankName(it) },
                    placeholder = "은행 입력",
                    modifier = Modifier.width(90.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Input(
                    value = uiState.accountNumber,
                    onChange = { viewModel.setAccountNumber(it) },
                    placeholder = "계좌번호 입력",
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = uiState.isSaveAccount,
                    onCheckedChange = { viewModel.setSaveAccount(it) },
                )

                Text("계좌번호 저장")
            }
            Spacer(modifier = Modifier.height(20.dp))
            CustomButton(
                "저장하기",
                modifier = Modifier.fillMaxWidth(),
                isDisable = uiState.imageUri == null
            ) {
                val uploadReceipt = {
                    viewModel.upload(
                        memberCouponId = couponId,
                        context = context,
                        onSuccess = {
                            // MyPage 데이터 갱신 (Activity 스코프 ViewModel 공유)
                            myPageViewModel?.refreshCoupons()
                            Toast.makeText(context, "영수증 제출 완료", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        },
                        onError = { message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                // 계좌번호 저장이 체크되어 있고 계좌 정보가 입력되어 있으면 먼저 저장
                if (uiState.isSaveAccount && uiState.bankName.isNotEmpty() && uiState.accountNumber.isNotEmpty()) {
                    viewModel.saveAccount(
                        onSuccess = {
                            uploadReceipt()
                        },
                        onError = { message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            uploadReceipt()  // 계좌 저장 실패해도 영수증은 제출
                        }
                    )
                } else {
                    uploadReceipt()
                }
            }
        }
    }
}
