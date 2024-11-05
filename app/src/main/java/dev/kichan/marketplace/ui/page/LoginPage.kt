import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import dev.kichan.marketplace.model.repository.MemberRepository
import dev.kichan.marketplace.model.repository.MemberRepositoryImpl
import dev.kichan.marketplace.model.service.MemberService
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginPage(navController: NavHostController) {
    val memberRepository = MemberRepositoryImpl()

    var inputId by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf<ResponseTemplate<LoginRes>?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    val login: (String, String) -> Unit = { id, pw ->
        result = null
        isLoading = true
        error = null

        CoroutineScope(Dispatchers.IO).launch {
            isLoading = false
            val res = memberRepository.login(body = LoginReq(id, pw))

            if (res.isSuccessful) {
                result = res.body()
            }
            else {
                Log.d("error", res.errorBody().toString())
                error = res.errorBody()?.string()
            }
        }
    }

    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            TextField(value = inputId, onValueChange = { inputId = it })
            TextField(
                value = inputPassword,
                onValueChange = { inputPassword = it },
                visualTransformation = PasswordVisualTransformation()
            )

            Button(onClick = { login(inputId, inputPassword) }) {
                Text(text = "로그인")
            }

            if (isLoading) Text(text = "로딩중")
            if (result != null) Text(text = result.toString())
            if(error != null) Text(text = error.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    MarketPlaceTheme {
        LoginPage(navController = rememberNavController())
    }
}
