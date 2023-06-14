package com.animebiru.kerjaaja.presentation.ui.detail

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentDetailBinding
import com.animebiru.kerjaaja.domain.models.Project
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.collectLatestFlowWhenStarted
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.custom_view.CustomSupportMapFragment
import com.animebiru.kerjaaja.presentation.viewmodels.DetailProjectViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.chip.Chip
import com.google.maps.android.ktx.addMarker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.Locale
import kotlin.time.Duration

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail), OnMenuItemClickListener, OnMapReadyCallback {

    private val binding by viewBindings(FragmentDetailBinding::bind)
    private val args: DetailFragmentArgs by navArgs()
    private val projectId by lazy { args.projectId }
    private val detailProjectViewModel: DetailProjectViewModel by viewModels()
    private var customSupportMapFragment: CustomSupportMapFragment? = null
    private var googleMap: GoogleMap? = null
    private val geocoder by lazy { Geocoder(requireContext(), Locale("ID")) }
    private val appBarConfiguration by lazy { AppBarConfiguration(setOf(R.id.homeFragment, R.id.historyFragment, R.id.profileFragment)) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailProjectViewModel.getDetailProject(projectId)
        customSupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as CustomSupportMapFragment
        binding.ctlDetailPage.setupWithNavController(binding.mtbDetailPage, findNavController(), appBarConfiguration)

        collectLatestFlowWhenStarted(detailProjectViewModel.detailProject) {
            val project = it ?: return@collectLatestFlowWhenStarted
            setupWithNewData(project)
        }

        collectLatestFlowWhenStarted(detailProjectViewModel.projectLocation) {
            val locality = it ?: return@collectLatestFlowWhenStarted
            binding.chipRegion.text = locality
        }

        binding.mtbDetailPage.setOnMenuItemClickListener(this)
        binding.fabChat.setOnClickListener { onMenuChatClicked() }
    }

    private fun setupWithNewData(project: Project) {
        if (googleMap == null) setupMaps() else showMap(project.latLng)
        with(binding) {
            detailProjectViewModel.getLocationName(project.latLng, geocoder)
            Glide.with(requireContext())
                .load(project.photoUrl)
                .into(iclHorizontalCardCreator.ivProfileImage)
            iclHorizontalCardCreator.tvCreator.text = project.creator
            livDescription.subtitleText = project.shortJobDesc
            livFee.subtitleText = project.fee
            val date = Instant.parse(project.createdAt).toLocalDateTime(TimeZone.UTC).date
            livDateCreated.subtitleText = "${date.dayOfMonth} ${date.month.name} ${date.year}"
        }
        for (categoryName in project.categories) {
            val chip = Chip(requireContext()).apply {
                text = categoryName
                chipIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_briefcase_filled)
                val typedValue = TypedValue()
                activity?.theme?.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)
                chipIconTint = ContextCompat.getColorStateList(requireContext(), typedValue.resourceId)
            }
            binding.chipGroup.addView(chip)
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menuChat -> true.also { onMenuChatClicked() }
            else -> false
        }
    }

    private fun showMap(latLng: LatLng) {
        googleMap?.addMarker { position(latLng) }
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
        customSupportMapFragment?.touchListener = CustomSupportMapFragment.CustomSupportMapFragmentOnTouchListener {
            binding.nsvDetailPage.requestDisallowInterceptTouchEvent(true)
        }
    }

    private fun setupMaps() {
        customSupportMapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val latLng = detailProjectViewModel.detailProject.value?.latLng ?: return
        showMap(latLng)
    }

    private fun onMenuChatClicked() {
        val receiverId = detailProjectViewModel.detailProject.value?.creator ?: return
        val action = DetailFragmentDirections.actionDetailFragmentToChatFragment(receiverId)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        binding.bnvMainBottomNavigation.setupWithNavController(findNavController())
    }

    private fun onMenuBookmarkClicked() {

    }
}