package com.animebiru.kerjaaja.presentation.ui.profile.security

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentNotificationBinding
import com.animebiru.kerjaaja.domain.utils.viewBindings

class NotificationFragment : Fragment(R.layout.fragment_notification) {
    private val binding by viewBindings(FragmentNotificationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

    }
}