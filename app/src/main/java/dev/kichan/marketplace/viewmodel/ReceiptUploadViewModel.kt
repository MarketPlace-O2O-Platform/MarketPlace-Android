package dev.kichan.marketplace.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.javafaker.Bool
import dev.kichan.marketplace.model.repository.PayBackCouponMemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

data class ReceiptUploadState(
    val imageUri: Uri? = null,
    val isUploading: Boolean = false,
    val error: String? = null
)

class ReceiptUploadViewModel(app: Application) : AndroidViewModel(app) {
    private val _state = MutableStateFlow(ReceiptUploadState())
    val state: StateFlow<ReceiptUploadState> = _state

    val paybackCouponRepo = PayBackCouponMemberRepository()

    fun setImage(uri: Uri?) {
        _state.value = _state.value.copy(imageUri = uri)
    }

    fun upload(mamberCouponId: Long) {
        val uri = _state.value.imageUri ?: return
        viewModelScope.launch {
            _state.value = _state.value.copy(isUploading = true, error = null)
            try {
                val (name, bytes) = readBytesFromUri(uri)
                val res = paybackCouponRepo.uploadRecept(mamberCouponId)

                _state.value = _state.value.copy(isUploading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isUploading = false, error = e.message)
            }
        }
    }

    private fun readBytesFromUri(uri: Uri): Pair<String, ByteArray> {
        val cr = getApplication<Application>().contentResolver
        val name = queryDisplayName(uri) ?: "photo_${System.currentTimeMillis()}.jpg"
        val bytes = cr.openInputStream(uri)?.use { it.readBytes() }
            ?: throw IllegalStateException("failed to read")
        return name to bytes
    }

    private fun queryDisplayName(uri: Uri): String? {
        val cr = getApplication<Application>().contentResolver
        val proj = arrayOf(android.provider.OpenableColumns.DISPLAY_NAME)
        cr.query(uri, proj, null, null, null)?.use { c ->
            val idx = c.getColumnIndexOrThrow(android.provider.OpenableColumns.DISPLAY_NAME)
            if (c.moveToFirst()) return c.getString(idx)
        }
        return null
    }
}