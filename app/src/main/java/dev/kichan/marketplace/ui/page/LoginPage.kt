package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.AuthViewModel
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.Input
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun LoginPage(navController: NavHostController, authViewModel: AuthViewModel) {
    var inputId by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val onLogin: (String, String) -> Unit = { id, password ->
        authViewModel.login(
            id = id,
            password = password,
            onSuccess = {
                navController.navigate(Page.Main.name)
                navController.popBackStack()
            },
            onFail = {
                // Handle login failure
            }
        )
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = Color.White)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            // Top Row with Image
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo), // Replace with your XML drawable resource
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .padding(0.dp)
                        .width(124.dp)
                        .height(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Description
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "문구문구문구문구문구문구문구문구문구문구문구",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.4.sp,
                        fontFamily = PretendardFamily,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF333333),
                    ),
                    modifier = Modifier

                        .fillMaxWidth()
                        .padding(1.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "문구문구문구문구문구문구문구문구문구",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 19.2.sp,
                        fontFamily = PretendardFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF333333),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp)
                )
            }

            Spacer(modifier = Modifier.height(46.79.dp))

            Text(
                text = "학교",

                // Body/Regular 14
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

            // Dropdown Selector
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(size = 2.dp)
                    )
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 2.dp))
                    .clickable { /* Logic for dropdown expansion */ }
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp)
                ) {
                    // Placeholder Text
                    Text(
                        text = "학교를 선택해주세요",
                        style = TextStyle(
                            fontSize = 13.sp,
                            lineHeight = 20.8.sp,
                            fontFamily = PretendardFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF838A94)
                        )
                    )

                    // Dropdown Icon
                    Icon(
                        painter = painterResource(id = R.drawable.down),
                        contentDescription = "드롭다운",
                        tint = Color(0xFF838A94),
                        modifier = Modifier.size(24.dp)
                    )
                }


                // Logic for Dropdown Menu (Placeholder)
                // Add DropdownMenu here if needed
//                DropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false },
//                    modifier = Modifier
//                        .border(
//                            width = 1.dp,
//                            color = Color(0xFFE0E0E0),
//                            shape = RoundedCornerShape(size = 2.dp)
//                        )
//                        .width(335.dp)
//                        .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 2.dp))
//                ) {
//                    val universities = listOf("인천대학교", "인하대학교", "연세대학교")
//                    universities.forEach { university ->
//                        DropdownMenuItem(
//                            onClick = {
//                                expanded = false
//                            },
//                            text = {
//                                Text(
//                                    text = university,
//                                    style = TextStyle(
//                                        fontSize = 13.sp,
//                                        lineHeight = 20.8.sp,
//                                        fontFamily = PretendardFamily,
//                                        fontWeight = FontWeight(400),
//                                        color = Color(0xFF000000)
//                                    )
//                                )
//                            }
//                        )
//                    }
//                }
            }


            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "학번(ID)",

                // Body/Regular 14
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
                onChange = { inputId = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "학번을 입력해주세요"

                )
            /*TextField(
                value = inputId,
                onValueChange = { inputId = it },
                label = { Text("학번을 입력해 주세요.") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )*/

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = "비밀번호",

                // Body/Regular 14
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

            // Pw Input
            Input(
                value = inputId,
                onChange = { inputId = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "비밀번호를 입력해주세요"

            )
            Spacer(modifier = Modifier.height(4.dp))


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "학교 포털 아이디/비밀번호를 통해 접속하실 수 있습니다.",

                // Body/Regular 14
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 19.2.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF333333),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
            )

            Spacer(modifier = Modifier.height(17.dp))

            // Login Button
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

            Spacer(modifier = Modifier.height(8.dp))

            // Checkboxes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = false, onCheckedChange = {})
                    Text(text = "학번(ID) 저장", fontSize = 14.sp)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = false, onCheckedChange = {})
                    Text(text = "비밀번호 저장", fontSize = 14.sp)
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
            authViewModel = AuthViewModel()
        )
    }
}
