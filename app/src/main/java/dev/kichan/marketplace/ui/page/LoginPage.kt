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
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.AuthViewModel
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.State
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun LoginPage(navController: NavHostController, authViewModel: AuthViewModel) {
    var inputId by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }

    val onLogin : (String, String) -> Unit = {id, password ->
        authViewModel.login(
            id = id,
            password = password,
            onSuccess = {
                navController.popBackStack()
                navController.navigate(Page.Main.name)
            },
            onFail = {
            }
        )
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

            Button(onClick = { onLogin(inputId, inputPassword) }) {
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
            AuthViewModel()
        )
    }
}
