import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.service.MemberService
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginPage(navController: NavHostController) {
    val retrofit = NetworkModule.getService(MemberService::class.java)

    var inputId by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }

    val login : (String, String) -> Unit = {id, pw ->
        CoroutineScope(Dispatchers.IO).launch {
            val res = retrofit.login(body = LoginReq(id, pw))

            Log.d("login", res.toString())
        }
    }

    Column {
        TextField(value = inputId, onValueChange = { inputId = it })
        TextField(value = inputPassword, onValueChange = { inputPassword = it })

        Button(onClick = { login(inputId, inputPassword) }) {
            Text(text = "로그인")
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
