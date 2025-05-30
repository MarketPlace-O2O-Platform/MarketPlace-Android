package dev.kichan.marketplace.ui.page

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.Input
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.InputType
import dev.kichan.marketplace.ui.theme.PretendardFamily
import dev.kichan.marketplace.viewmodel.AuthViewModel
import dev.kichan.marketplace.viewmodel.LoginUiState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    navController: NavHostController,
    authViewModel: AuthViewModel = AuthViewModel()
) {
    val context = LocalContext.current
    val state = authViewModel.loginState

    var inputId by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("학교 포털 아이디/비밀번호를 통해 접속하실 수 있습니다.") }
    var showError by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    var expanded by remember { mutableStateOf(false) }
    var selectedSchool by remember { mutableStateOf("학교를 선택해주세요") }
    val schools = listOf("인천대학교")

    val onLogin: (String, String) -> Unit = { id, password ->
        authViewModel.login(
            id = id,
            password = password,
        )
    }

    when (state) {
        is LoginUiState.Error -> {
            if (state.message.isNotEmpty()) {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
        }

        LoginUiState.Idle -> {}
        LoginUiState.Loading -> {}
        is LoginUiState.Success -> {
            navController.popBackStack()
            navController.navigate(Page.Main.name)
        }
    }

    if (showError) {
        LaunchedEffect(Unit) {
            message = "다시 입력해주세요."

            showError = false
            delay(5000)
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(WindowInsets.ime.asPaddingValues())
                .background(color = Color.White)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Spacer(modifier = Modifier.height(100.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .width(124.dp)
                        .height(40.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight(500)
                            )
                        ) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("매번")
                            }
                            append(" 마라탕 한 그릇,")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(" 이천원 더")
                            }
                            append(" 내고 있어요.\n")
                        }

                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(500)
                            )
                        ) {
                            append("이제, 다니는")
                            withStyle(style = SpanStyle(fontWeight = FontWeight(600))) {
                                append(" 대학")
                            }
                            append(" 제휴 멤버십으로\n")

                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("쿠폰 꾸러미")
                            }
                            append(" 받아볼까요?")
                        }
                    },
                    fontFamily = PretendardFamily,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            // 학교 선택 드롭다운
            Text(
                text = "학교",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 22.4.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF333333),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded } // 드롭다운 상태 토글
            ) {
                // 드롭다운 트리거
                OutlinedTextField(
                    value = selectedSchool,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(), // 드롭다운 메뉴와 연결
                    textStyle = TextStyle( // 수정됨: 텍스트 스타일 정의
                        fontSize = 13.sp,
                        lineHeight = 20.8.sp,
                        fontFamily = PretendardFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF838A94)
                    ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF333333), // 수정됨: 포커스 시 테두리 색상 변경
                        unfocusedBorderColor = Color(0xFFAAAAAA) // 수정됨: 비포커스 시 테두리 색상 변경
                    )
                )

                // 드롭다운 메뉴
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    schools.forEach { school ->
                        DropdownMenuItem(
                            text = { Text(text = school) },
                            onClick = {
                                selectedSchool = school
                                expanded = false // 선택 후 드롭다운 닫기
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "학번(ID)",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 22.4.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF333333),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // ID Input
            Input(
                value = inputId,
                onChange = {
                    if (it.all { char -> char.isDigit() }) {
                        inputId = it
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "학번을 입력해주세요(숫자만)"
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "비밀번호",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 22.4.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF333333),
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Pw Input
            Input(
                value = inputPassword,
                onChange = { inputPassword = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "비밀번호를 입력해주세요",
                inputType = InputType.Password
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 19.2.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF333333),
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(17.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(335.dp)
                    .height(38.dp)
                    .background(color = Color(0xFF303030), shape = RoundedCornerShape(size = 4.dp))
            ) {
                Button(
                    onClick = { onLogin(inputId, inputPassword) },
                    modifier = Modifier.fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "로그인",
                        style = TextStyle(fontSize = 14.sp, color = Color.White)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    MarketPlaceTheme {
        LoginPage(
            navController = rememberNavController(),
        )
    }
}
