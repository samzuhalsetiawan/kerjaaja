package com.animebiru.kerjaaja.presentation.ui.activity.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.window.OnBackInvokedDispatcher
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.ActivityMapsBinding
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.listener.OnDialogPositiveButtonClickListener
import com.animebiru.kerjaaja.presentation.ui.dialog.DialogSimple
import com.animebiru.kerjaaja.presentation.viewmodels.MapsViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(R.layout.activity_maps), OnMapReadyCallback {
    private val binding by viewBindings(ActivityMapsBinding::bind)
    private val mapsViewModel: MapsViewModel by viewModels()
    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>
    private var googleMap: GoogleMap? = null
    private val fusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private var placesClient: PlacesClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), permissionsActivityResultCallback)

        if (isAllPermissionGranted()) {
            if (isServicesOk()) {
                initMaps()
            }
        } else {
            permissionsLauncher.launch(
                REQUIRED_PERMISSION.filter {
                    ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
                }.toTypedArray()
            )
        }
        mapsViewModel.selectedLocation.observe(this) {
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 12f))
        }
        onBackPressedDispatcher.addCallback(this) {
            val selectedMarker = mapsViewModel.selectedMarker.value
            val selectedLocation = mapsViewModel.selectedLocation.value
            when {
                selectedMarker != null -> {
                    val intent = Intent().putExtra(EXTRA_LOCATION, "${selectedMarker.position.latitude}, ${selectedMarker.position.longitude}")
                    setResult(RESULT_OK, intent)
                    mapsViewModel.clearSelectedMarker()
                }
                selectedLocation != null -> {
                    val intent = Intent().putExtra(EXTRA_LOCATION, "${selectedLocation.latitude}, ${selectedLocation.longitude}")
                    setResult(RESULT_OK, intent)
                }
                else -> setResult(RESULT_CANCELED)
            }
            finish()
        }
    }

    private fun getUserCurrentLocation() {
        mapsViewModel.getUserCurrentLocation(fusedLocationProviderClient)
    }

    private val permissionsActivityResultCallback = ActivityResultCallback<Map<String, Boolean>> { result ->
        if (result.all { ActivityCompat.checkSelfPermission(this, it.key) == PackageManager.PERMISSION_GRANTED }) initMaps().also { return@ActivityResultCallback }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)  {
            if (result.any { shouldShowRequestPermissionRationale(it.key) }) {
                dialogPermissionLocationRationale.onPositiveButtonClickListener = OnDialogPositiveButtonClickListener {
                    permissionsLauncher.launch(REQUIRED_PERMISSION.toTypedArray())
                    return@OnDialogPositiveButtonClickListener
                }
                dialogPermissionLocationRationale.show(supportFragmentManager, null)
            }
        }
        dialogPermissionNotGranted.show(supportFragmentManager, null)
    }

    private fun isAllPermissionGranted(): Boolean {
        return REQUIRED_PERMISSION.all { ActivityCompat.checkSelfPermission(this,it) == PackageManager.PERMISSION_GRANTED }
    }

    private fun initMaps() {
        val supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        googleMap?.isMyLocationEnabled = true
        googleMap?.uiSettings?.apply {
            isCompassEnabled = true
            setAllGesturesEnabled(true)
            isIndoorLevelPickerEnabled = true
            isMapToolbarEnabled = true
        }
        googleMap?.setOnMapLongClickListener {
            createMarker(it)?.let { marker ->
                mapsViewModel.setSelectedMarker(marker)
            }
        }
        getUserCurrentLocation()
        initPlacesService()
    }

    private fun createMarker(latLng: LatLng, title: String? = null): Marker? {
        val markerOption = MarkerOptions().apply {
            position(latLng)
            title(title ?: "${latLng.latitude}, ${latLng.longitude}")
        }
        return googleMap?.addMarker(markerOption).also { it?.showInfoWindow() }
    }

    private fun initPlacesService() {
        Places.initialize(applicationContext, "AIzaSyDgO9pQ41ibD3MEJASNHYwo0ALZ7AqTeIk")
        placesClient = Places.createClient(this)
        val acSearchResult = supportFragmentManager.findFragmentById(R.id.acSfSearchResult) as AutocompleteSupportFragment
        acSearchResult.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
        acSearchResult.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i("MY_DEBUG", "Place: ${place.name}, ${place.id}")
                place.latLng?.let {
                    mapsViewModel.setSelectedLocation(it)
                    createMarker(it, place.name)?.let { marker ->
                        mapsViewModel.setSelectedMarker(marker)
                    }
                }
            }
            override fun onError(status: Status) {
                Log.i("MY_DEBUG", "An error occurred: $status")
            }
        })


    }

    private fun isServicesOk(): Boolean {
        Log.d("MY_DEBUG:${this@MapsActivity.javaClass.simpleName}", "isServicesOk: Checking...")
        val available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if (available ==  ConnectionResult.SUCCESS) {
            Log.d("MY_DEBUG:${this@MapsActivity.javaClass.simpleName}", "isServicesOk: services available")
            return true
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d("MY_DEBUG:${this@MapsActivity.javaClass.simpleName}", "isServicesOk: Error occurred but we can fix it")
            val dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOG_REQUEST)
            dialog?.show()
        } else {
            Log.d("MY_DEBUG:${this@MapsActivity.javaClass.simpleName}", "isServicesOk: services not available, nothing we can do")
        }
        return false
    }

    private val dialogPermissionLocationRationale by lazy { DialogSimple().apply {
        title = "Kami perlu izin anda"
        message = "Kami perlu izin untutk mengakses lokasi device anda agar dapat menampilkan map"
    } }

    private val dialogPermissionNotGranted by lazy { DialogSimple().apply {
        title = "Aplikasi tidak memiliki izin"
        message = "Kami tidak dapat menampilkan map karena ada tidak memberikan izin akses lokasi device anda, mohon ubah perizinan melalui settings"
        positiveButtonLabel = "Settings"
        onPositiveButtonClickListener = OnDialogPositiveButtonClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.fromParts("package", this@MapsActivity.packageName, null)
            startActivity(intent)
        }
    } }

    companion object {
        private const val ERROR_DIALOG_REQUEST = 9001
        const val EXTRA_LOCATION = "com.animebiru.kerjaaja.EXTRA_LOCATION"
        val REQUIRED_PERMISSION = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}