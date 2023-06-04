package com.animebiru.kerjaaja.presentation.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentDetailBinding
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.custom_view.CustomSupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.addMarker

class DetailFragment : Fragment(R.layout.fragment_detail), OnMenuItemClickListener, OnMapReadyCallback {

    private val binding by viewBindings(FragmentDetailBinding::bind)
    private var customSupportMapFragment: CustomSupportMapFragment? = null
    private var googleMap: GoogleMap? = null
    private val appBarConfiguration by lazy { AppBarConfiguration(setOf(R.id.homeFragment, R.id.historyFragment, R.id.profileFragment)) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customSupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as CustomSupportMapFragment
        binding.ctlDetailPage.setupWithNavController(binding.mtbDetailPage, findNavController(), appBarConfiguration)
        binding.mtbDetailPage.setOnMenuItemClickListener(this)

        if (true) {
            if (googleMap == null) {
                setupMaps()
            } else {
                showMap()
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menuChat -> true.also { onMenuChatClicked() }
            else -> false
        }
    }

    private fun showMap() {
        googleMap?.addMarker {
            position(LatLng(-33.852, 151.211))
            title("sydney")
        }
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-33.852, 151.211), 16f))
        customSupportMapFragment?.touchListener = CustomSupportMapFragment.CustomSupportMapFragmentOnTouchListener {
            binding.nsvDetailPage.requestDisallowInterceptTouchEvent(true)
        }
    }

    private fun setupMaps() {
        customSupportMapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        showMap()
    }

    private fun onMenuChatClicked() {
        val action = DetailFragmentDirections.actionDetailFragmentToChatFragment()
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        binding.bnvMainBottomNavigation.setupWithNavController(findNavController())
    }

    private fun onMenuBookmarkClicked() {

    }
}