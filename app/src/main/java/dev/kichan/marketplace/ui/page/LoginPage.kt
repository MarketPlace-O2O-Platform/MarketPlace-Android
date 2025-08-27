package dev.kichan.marketplace.ui.page

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.DropDownMenu
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.atoms.CustomButton
import dev.kichan.marketplace.ui.component.atoms.Input
import dev.kichan.marketplace.ui.component.atoms.InputType
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily
import dev.kichan.marketplace.viewmodel.LoginUiState
import dev.kichan.marketplace.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginPage(
    navController: NavHostController,
    loginViewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val state by loginViewModel.loginState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val loginInputState by loginViewModel.loginInputState.collectAsStateWithLifecycle()

    val onLogin: () -> Unit = {
        loginViewModel.login()
    }

    LaunchedEffect(state) {
        when (state) {
            is LoginUiState.Error -> {
                Toast.makeText(context, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }

            LoginUiState.Authenticated -> {
                coroutineScope.launch {
                    Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show()
                    navController.navigate(Page.Main.name) {
                        popUpTo(Page.Login.name) { inclusive = true }
                    }
                }
            }

            else -> {}
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
                    text = "이제, 인천대 제휴 할인을 받으러 가볼까요?",
                    fontFamily = PretendardFamily,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


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

            DropDownMenu(loginInputState.selectedSchool, listOf("인천대학교"), placeholder = "학교를 선택해주세요") {
                loginViewModel.setSelectedSchool(it)
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
                value = loginInputState.studentId,
                onChange = {
                    if (it.all { char -> char.isDigit() }) {
                        loginViewModel.setStudentId(it)
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
                value = loginInputState.password,
                onChange = { loginViewModel.setPassword(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "비밀번호를 입력해주세요",
                inputType = InputType.Password
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "학교 포털 아이디/비밀번호를 통해 접속하실 수 있습니다.",
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

            Spacer(modifier = Modifier.height(20.dp))

            CustomButton(
                text = "로그인",
                modifier = Modifier.fillMaxWidth(),
                isDisable = !(loginInputState.studentId.isNotEmpty() && loginInputState.password.isNotEmpty() && loginInputState.selectedSchool.isNotEmpty()),
            ) {
                onLogin()
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