import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.MainViewModel
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.State
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginPage(navController: NavHostController, viewModel: MainViewModel) {
    var inputId by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }

    val state by viewModel.state.observeAsState()
    val userData by viewModel.member.observeAsState()

    LaunchedEffect(userData) {
        if(userData != null) {
            navController.navigate(Page.Main.name)
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

            if(state == State.Success) {
                Text(text = "성공")
            }
            else if(state == State.Loading)
                Text(text = "로딩중")
            else if(state == State.Erroe)
                Text(text = "에러")

            Text(text = userData.toString())

            Button(onClick = { viewModel.login(inputId, inputPassword) }) {
                Text(text = "로그인")
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
            viewModel = MainViewModel()
        )
    }
}
