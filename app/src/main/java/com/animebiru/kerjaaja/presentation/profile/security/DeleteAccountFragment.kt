package com.animebiru.kerjaaja.presentation.profile.security

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentDeleteAccountBinding
import com.animebiru.kerjaaja.domain.utils.viewBindings


class DeleteAccountFragment : Fragment(R.layout.fragment_delete_account) {
    private val binding by viewBindings(FragmentDeleteAccountBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_account, container, false)
    }

    companion object {
    }
}