package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dev.kichan.marketplace.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class LoginEvent {
    object Success : LoginEvent()
    data class Error(val message: String) : LoginEvent()
    object Cancelled : LoginEvent()
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val googleSignInClient: GoogleSignInClient

    private val _loginEvent = MutableSharedFlow<LoginEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(application, gso)
    }

    fun getGoogleSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    fun handleGoogleSignInResult(data: Intent?) {
        viewModelScope.launch {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { idToken ->
                    val credential = GoogleAuthProvider.getCredential(idToken, null)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {
                                viewModelScope.launch { _loginEvent.emit(LoginEvent.Success) }
                            } else {
                                viewModelScope.launch { _loginEvent.emit(LoginEvent.Error("Google 로그인 실패: ${authTask.exception?.message}")) }
                            }
                        }
                } ?: run {
                    viewModelScope.launch { _loginEvent.emit(LoginEvent.Error("Google ID Token이 없습니다.")) }
                }
            } catch (e: ApiException) {
                viewModelScope.launch { _loginEvent.emit(LoginEvent.Error("Google 로그인 실패: ${e.message}")) }
            }
        }
    }

    fun handleGoogleSignInCancelled() {
        viewModelScope.launch { _loginEvent.emit(LoginEvent.Cancelled) }
    }
}
