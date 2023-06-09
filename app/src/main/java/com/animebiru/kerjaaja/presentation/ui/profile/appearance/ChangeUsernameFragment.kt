package com.animebiru.kerjaaja.presentation.ui.profile.appearance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentChangePhotoProfileBinding
import com.animebiru.kerjaaja.databinding.FragmentChangeUsernameBinding
import com.animebiru.kerjaaja.domain.utils.viewBindings


class ChangeUsernameFragment : Fragment(R.layout.fragment_change_username) {
    private val binding by viewBindings(FragmentChangeUsernameBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}