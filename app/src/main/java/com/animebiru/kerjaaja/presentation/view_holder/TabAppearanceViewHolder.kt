package com.animebiru.kerjaaja.presentation.view_holder

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.presentation.custom_view.ListItemView
import com.animebiru.kerjaaja.presentation.ui.profile.ProfileFragmentDirections

class TabAppearanceViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private val cvChangePhotoProfile: ListItemView = view.findViewById(R.id.cvChangePhotoProfile)
    private val cvChangeUsername: ListItemView = view.findViewById(R.id.cvChangeUsername)
    private val cvChangeTheme: ListItemView = view.findViewById(R.id.cvChangeTheme)

    init {
        cvChangePhotoProfile.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChangePhotoProfileFragment()
            view.findNavController().navigate(action)
        }

        cvChangeUsername.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChangeUsernameFragment()
            view.findNavController().navigate(action)
        }

        cvChangeTheme.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChangeThemeFragment()
            view.findNavController().navigate(action)
        }
    }
}