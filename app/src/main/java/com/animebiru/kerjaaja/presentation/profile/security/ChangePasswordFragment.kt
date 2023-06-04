package com.animebiru.kerjaaja.presentation.profile.security

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentChangePasswordBinding
import com.animebiru.kerjaaja.databinding.FragmentChangePhotoProfileBinding
import com.animebiru.kerjaaja.domain.utils.viewBindings


class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {
    private val binding by viewBindings(FragmentChangePasswordBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    companion object {

    }
}