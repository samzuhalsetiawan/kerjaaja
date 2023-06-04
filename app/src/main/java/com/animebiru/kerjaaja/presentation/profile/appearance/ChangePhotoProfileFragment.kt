package com.animebiru.kerjaaja.presentation.profile.appearance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentChangePhotoProfileBinding
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.google.android.material.bottomsheet.BottomSheetDialog


class ChangePhotoProfileFragment : Fragment(R.layout.fragment_change_photo_profile) {
    private val binding by viewBindings(FragmentChangePhotoProfileBinding::bind)
    private lateinit var  viewBottom: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivProfilePicture.setOnClickListener {
            viewBottom = layoutInflater.inflate(R.layout.item_bottom_change_profile_picture,requireActivity().findViewById(R.id.changePhotoProfileFragment), false)
            val parentView = viewBottom.parent as? ViewGroup
            parentView?.removeView(viewBottom)

            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(viewBottom)
            dialog.show()
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_photo_profile, container, false)
    }


}