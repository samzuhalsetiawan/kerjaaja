package com.animebiru.kerjaaja.presentation.ui.dialog

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.ItemBottomChangeProfilePictureBinding
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.listener.OnPhotoSourceSelectedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogBottomPhotoSourcePicker: BottomSheetDialogFragment(R.layout.item_bottom_change_profile_picture) {
    private val binding by viewBindings(ItemBottomChangeProfilePictureBinding::bind)

    var photoSourceSelectedListener: OnPhotoSourceSelectedListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivGallery.setOnClickListener {
            photoSourceSelectedListener?.onPhotoSourceFromGallerySelected()
            dismiss()
        }
    }

}