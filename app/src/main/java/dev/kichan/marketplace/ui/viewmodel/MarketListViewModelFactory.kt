package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.kichan.marketplace.common.LargeCategory

class MarketListViewModelFactory(private val application: Application, private val category: LargeCategory) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketListViewModel::class.java)) {
            return MarketListViewModel(application, category) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
