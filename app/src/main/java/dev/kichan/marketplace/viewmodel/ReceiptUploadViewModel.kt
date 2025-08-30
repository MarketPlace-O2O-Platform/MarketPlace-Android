package dev.kichan.marketplace.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
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

    fun upload(memberCouponId: Long, context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val body = uriToMultipartBody(context, _state.value.imageUri!!, "image")!!
            membersRepository.uploadReceipt(memberCouponId, body)
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }
}