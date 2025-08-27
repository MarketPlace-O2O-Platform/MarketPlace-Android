package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MarketDetailViewModelFactory(private val application: Application, private val marketId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketDetailViewModel::class.java)) {
            return MarketDetailViewModel(application, marketId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
