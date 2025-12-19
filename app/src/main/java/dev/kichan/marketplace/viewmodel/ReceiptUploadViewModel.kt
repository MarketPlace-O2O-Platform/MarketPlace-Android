package dev.kichan.marketplace.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.MemberAccountReq
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

data class ReceiptUploadUiState(
    val imageUri: Uri? = null,
    val bankName: String = "",
    val accountNumber: String = "",
    val isSaveAccount: Boolean = false,
)

class ReceiptUploadViewModel : ViewModel() {
    private val membersRepository = RepositoryProvider.provideMembersRepository()
    private val _state = MutableStateFlow(ReceiptUploadUiState())
    val state = _state.asStateFlow()

    fun uriToMultipartBody(context: Context, uri: Uri, partName: String = "file"): MultipartBody.Part? {
        val contentResolver = context.contentResolver
        val fileName = getFileName(contentResolver, uri) ?: "temp_file"
        val mimeType = contentResolver.getType(uri) ?: "image/*"

        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val bytes = inputStream.readBytes()
        inputStream.close()

        val requestBody = RequestBody.create(mimeType.toMediaTypeOrNull(), bytes)
        return MultipartBody.Part.createFormData(partName, fileName, requestBody)
    }

    private fun getFileName(contentResolver: ContentResolver, uri: Uri): String? {
        var name: String? = null
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index >= 0) name = it.getString(index)
            }
        }
        return name
    }

    fun setImage(uri: Uri?) {
        _state.update { it.copy(imageUri = uri) }
    }

    fun setBankName(name: String) {
        _state.update { it.copy(bankName = name) }
    }

    fun setAccountNumber(number: String) {
        _state.update { it.copy(accountNumber = number) }
    }

    fun setSaveAccount(isSave: Boolean) {
        _state.update { it.copy(isSaveAccount = isSave) }
    }

    fun loadSavedAccount() {
        viewModelScope.launch {
            try {
                val response = membersRepository.getMember()
                if (response.isSuccessful) {
                    val member = response.body()?.response
                    member?.let {
                        _state.update { state ->
                            state.copy(
                                bankName = it.account,
                                accountNumber = it.accountNumber,
                                isSaveAccount = true
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("ReceiptUploadViewModel", "계좌 정보 조회 실패", e)
                }
            }
        }
    }

    fun saveAccount(onSuccess: (() -> Unit)? = null, onError: ((String) -> Unit)? = null) {
        viewModelScope.launch {
            try {
                val accountReq = MemberAccountReq(
                    account = _state.value.bankName,
                    accountNumber = _state.value.accountNumber
                )
                val response = membersRepository.permitAccount(accountReq)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        onSuccess?.invoke()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError?.invoke("계좌 정보 저장 실패")
                    }
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("ReceiptUploadViewModel", "계좌 정보 저장 실패", e)
                }
                withContext(Dispatchers.Main) {
                    onError?.invoke("계좌 정보 저장 중 오류 발생")
                }
            }
        }
    }

    fun denyAccount(onSuccess: (() -> Unit)? = null, onError: ((String) -> Unit)? = null) {
        viewModelScope.launch {
            try {
                val response = membersRepository.denyAccount()
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        onSuccess?.invoke()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError?.invoke("계좌 정보 해제 실패")
                    }
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("ReceiptUploadViewModel", "계좌 정보 해제 실패", e)
                }
                withContext(Dispatchers.Main) {
                    onError?.invoke("계좌 정보 해제 중 오류 발생")
                }
            }
        }
    }

    fun upload(memberCouponId: Long, context: Context, onSuccess: () -> Unit, onError: ((String) -> Unit)? = null) {
        viewModelScope.launch {
            try {
                val imageUri = _state.value.imageUri
                if (imageUri == null) {
                    withContext(Dispatchers.Main) {
                        onError?.invoke("이미지를 선택해주세요")
                    }
                    return@launch
                }

                val body = uriToMultipartBody(context, imageUri, "image")
                if (body == null) {
                    withContext(Dispatchers.Main) {
                        onError?.invoke("이미지 처리 중 오류가 발생했습니다")
                    }
                    return@launch
                }

                val response = membersRepository.uploadReceipt(memberCouponId, body)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError?.invoke("영수증 업로드 실패: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("ReceiptUploadViewModel", "영수증 업로드 실패: memberCouponId=$memberCouponId", e)
                }
                withContext(Dispatchers.Main) {
                    onError?.invoke("업로드 중 오류가 발생했습니다")
                }
            }
        }
    }
}