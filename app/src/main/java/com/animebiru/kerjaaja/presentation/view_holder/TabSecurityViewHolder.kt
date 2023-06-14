package com.animebiru.kerjaaja.presentation.view_holder

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.presentation.custom_view.ListItemView
import com.animebiru.kerjaaja.presentation.ui.profile.ProfileFragmentDirections

class TabSecurityViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private val cvChangePassword: ListItemView = view.findViewById(R.id.cvChangePassword)
    private val cvNotification: ListItemView = view.findViewById(R.id.cvNotification)
    private val cvDeleteAccount: ListItemView = view.findViewById(R.id.cvDeleteAccount)

    init {
        cvChangePassword.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
            view.findNavController().navigate(action)
        }

        cvNotification.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToNotificationFragment()
            view.findNavController().navigate(action)
        }

        cvDeleteAccount.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToDeleteAccountFragment()
            view.findNavController().navigate(action)
        }
    }
}