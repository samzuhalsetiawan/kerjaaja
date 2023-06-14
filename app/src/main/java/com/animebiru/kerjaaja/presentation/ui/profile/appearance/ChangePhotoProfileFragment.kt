package com.animebiru.kerjaaja.presentation.ui.profile.appearance

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentChangePhotoProfileBinding
import com.animebiru.kerjaaja.domain.events.UserEvent
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.collectLatestFlowWhenStarted
import com.animebiru.kerjaaja.domain.utils.HelperUtil
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.listener.OnPhotoSourceSelectedListener
import com.animebiru.kerjaaja.presentation.ui.dialog.DialogActionSuccess
import com.animebiru.kerjaaja.presentation.ui.dialog.DialogBottomPhotoSourcePicker
import com.animebiru.kerjaaja.presentation.viewmodels.ProfileSettingsViewModel
import com.animebiru.kerjaaja.presentation.viewmodels.UserViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class ChangePhotoProfileFragment :
    Fragment(R.layout.fragment_change_photo_profile),
    OnPhotoSourceSelectedListener,
    OnMenuItemClickListener
{
    private val binding by viewBindings(FragmentChangePhotoProfileBinding::bind)
    private val userViewModel: UserViewModel by viewModels()
    private val profileSettingsViewModel: ProfileSettingsViewModel by viewModels()
    private val viewBottomDialog by lazy { DialogBottomPhotoSourcePicker().also { it.photoSourceSelectedListener = this } }
    private lateinit var mediaPickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private val dialogActionSuccess by lazy { DialogActionSuccess().also { it.message = "Profile Picture Changed" } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaPickerLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia(), onMediaPickerResult)

        userViewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                is UserEvent.OnChangePhotoProfileSuccess -> showSuccessDialog()
                else -> Unit
            }
        }

        collectLatestFlowWhenStarted(userViewModel.detailUser) {
            val user = it ?: return@collectLatestFlowWhenStarted
            binding.tvUsername.text = user.fullName
            Glide.with(requireContext())
                .load(user.photoUrl)
                .into(binding.ivProfilePicture)
        }

        profileSettingsViewModel.currentSelectedPhotoProfile.observe(viewLifecycleOwner) {
            updateImageView(file = it)
        }

        binding.ivProfilePicture.setOnClickListener {
            viewBottomDialog.show(parentFragmentManager, null)
        }

        binding.mtbChangePhotoProfile.setOnMenuItemClickListener(this)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menuCreate -> changeProfilePicture().let { true }
            else -> false
        }
    }

    private fun changeProfilePicture() {
        val photo = profileSettingsViewModel.currentSelectedPhotoProfile.value ?: return
        userViewModel.changeUserProfilePicture(photo)
    }

    override fun onPhotoSourceFromGallerySelected() {
        mediaPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    override fun onPhotoSourceFromCameraSelected() {

    }

    private val onMediaPickerResult = ActivityResultCallback<Uri?> {
        if (it == null) return@ActivityResultCallback
        val imageFile = HelperUtil.uriToFile(it, requireContext())
        profileSettingsViewModel.setCurrentSelectedPhotoProfile(imageFile)
    }

    private fun updateImageView(uri: Uri? = null, file: File? = null) {
        if (uri == null && file == null) return
        Glide.with(requireContext()).let {
            if (uri == null) it.load(file) else it.load(uri)
        }.into(binding.ivProfilePicture)
    }

    private fun showSuccessDialog() {
        userViewModel.notifyTransactionSuccess()
        dialogActionSuccess.show(parentFragmentManager, null)
    }

}