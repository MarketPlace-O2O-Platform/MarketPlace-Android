package dev.kichan.marketplace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.kichan.marketplace.model.data.login.LoginRes

class SingleTonViewModel : ViewModel() {
    val currentMember = MutableLiveData<LoginRes>(LoginRes(202401598))
}