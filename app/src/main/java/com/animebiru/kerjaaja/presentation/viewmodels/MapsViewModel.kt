package com.animebiru.kerjaaja.presentation.viewmodels

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(): ViewModel() {

    private val _selectedLocation: MutableLiveData<LatLng> = MutableLiveData()
    val selectedLocation: LiveData<LatLng> = _selectedLocation

    private val _selectedMarker: MutableLiveData<Marker?> = MutableLiveData()
    val selectedMarker: LiveData<Marker?> = _selectedMarker

    @SuppressLint("MissingPermission")
    fun getUserCurrentLocation(client: FusedLocationProviderClient) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentLocationRequest = CurrentLocationRequest.Builder().build()
                val currentLocation = client.getCurrentLocation(currentLocationRequest, null).await()
                _selectedLocation.postValue(LatLng(currentLocation.latitude, currentLocation.longitude))
            } catch (e: Throwable) {
                Log.e("MapsViewModel", "Failed to get user current location")
            }
        }
    }

    fun setSelectedLocation(location: LatLng) {
        _selectedLocation.value = location
    }

    fun setSelectedMarker(marker: Marker) {
        _selectedMarker.value?.remove()
        _selectedMarker.value = marker
    }

    fun clearSelectedMarker() {
        _selectedMarker.value = null
    }

}