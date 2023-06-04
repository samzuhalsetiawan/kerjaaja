package com.animebiru.kerjaaja.domain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.domain.utils.HelperDummyData
import com.animebiru.kerjaaja.presentation.custom_view.ListItemView
import com.animebiru.kerjaaja.presentation.profile.ProfileFragmentDirections

class ProfileSectionsPagerAdapter(private val parentFragment: Fragment):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class TabAppearanceViewHolder(private val view: View): RecyclerView.ViewHolder(view){
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
    class TabJobsViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val rvTabJobs = view.rootView as RecyclerView
    }
    class TabSecurityViewHolder(private val view: View): RecyclerView.ViewHolder(view){
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

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TAB_APPEARANCE
            1 -> TAB_JOBS
            2 -> TAB_SECURITY
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TAB_APPEARANCE -> TabAppearanceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_tab_appearance, parent, false))
            TAB_JOBS -> TabJobsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_tab_jobs, parent, false))
            TAB_SECURITY -> TabSecurityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_tab_security, parent, false))
            else -> throw Exception("Tabs not implemented, viewType: $viewType")
        }
    }

    private fun setupTabJobs(holder: TabJobsViewHolder) {
        val jobsAdapter = HomeRecommendationAdapter()
        jobsAdapter.listener = HomeRecommendationAdapter.HomeRecommendationAdapterListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToDetailFragment()
            parentFragment.findNavController().navigate(action)
        }
        holder.rvTabJobs.apply {
            adapter = jobsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        jobsAdapter.listOfJob = HelperDummyData.dummyJob
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TabAppearanceViewHolder -> Unit
            is TabJobsViewHolder -> setupTabJobs(holder)
            is TabSecurityViewHolder -> Unit
        }
    }

    override fun getItemCount(): Int = 3

    companion object {
        private const val TAB_APPEARANCE = 0
        private const val TAB_JOBS = 1
        private const val TAB_SECURITY = 2
    }
}