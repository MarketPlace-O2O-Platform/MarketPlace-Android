package dev.kichan.marketplace.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.RequestMarketCreateReq
import dev.kichan.marketplace.model.dto.kakao.local.Place
import dev.kichan.marketplace.model.repository.KakaoRepository
import dev.kichan.marketplace.model.repository.RequestMarketsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RequestViewModel : ViewModel() {
    private val kakaoRepository: KakaoRepository = RepositoryProvider.provideKakaoRepository()
    private val requestMarketsRepository: RequestMarketsRepository = RepositoryProvider.providerRequestMarketRepository()

    val searchQuery = mutableStateOf("")
    val searchResults = mutableStateOf<List<Place>>(emptyList())
    val selectedPlace = mutableStateOf<Place?>(null)

    private val _requestResult = MutableSharedFlow<Boolean>()
    val requestResult = _requestResult.asSharedFlow()

    fun searchPlaces() {
        viewModelScope.launch {
            if (searchQuery.value.isNotBlank()) {
                try {
                    val response = kakaoRepository.searchByKeyword(searchQuery.value)
                    if (response.isSuccessful) {
                        searchResults.value = response.body()?.documents ?: emptyList()
                    } else {
                        // Handle error
                        searchResults.value = emptyList()
                    }
                } catch (e: Exception) {
                    // Handle exception
                    searchResults.value = emptyList()
                }
            } else {
                searchResults.value = emptyList()
            }
        }
    }

    fun onPlaceSelected(place: Place) {
        selectedPlace.value = place
    }

    fun createRequestMarket() {
        viewModelScope.launch {
            selectedPlace.value?.let {
                try {
                    val request = RequestMarketCreateReq(
                        name = it.place_name,
                        address = it.road_address_name
                    )
                    val response = requestMarketsRepository.createRequestMarket(request)
                    _requestResult.emit(response.isSuccessful)
                } catch (e: Exception) {
                    _requestResult.emit(false)
                }
            }
        }
    }
}
