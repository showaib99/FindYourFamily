package com.example.findyourfamily.viewmodel



import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnCompleteListener

class LocationViewModel : ViewModel() {
    private var fusedLocationClient: FusedLocationProviderClient? = null

    fun getLastLocation(callback: (String) -> Unit) {
        fusedLocationClient?.lastLocation
            ?.addOnCompleteListener(OnCompleteListener{
                if (it.isSuccessful && it.result != null) {
                    val location = it.result
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val locationString = "Lat: $latitude, Long: $longitude"
                    callback(locationString)
                } else {
                    callback("Location not available")
                }
            })
    }

    fun initializeFusedLocationClient(client: FusedLocationProviderClient){
        fusedLocationClient = client
    }

}