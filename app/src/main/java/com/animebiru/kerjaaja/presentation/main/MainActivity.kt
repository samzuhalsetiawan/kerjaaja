package com.animebiru.kerjaaja.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.animebiru.kerjaaja.databinding.ActivityMainBinding
import com.animebiru.kerjaaja.databinding.FragmentChangeThemeBinding
import com.animebiru.kerjaaja.domain.sealed_class.MainActivityEvents
import com.animebiru.kerjaaja.presentation.dialog.DialogError
import com.animebiru.kerjaaja.presentation.profile.appearance.theme.ChangeThemeViewModel
import com.animebiru.kerjaaja.presentation.profile.appearance.theme.SettingsThemePreferences
import com.animebiru.kerjaaja.presentation.profile.appearance.ViewModelFactory
import com.animebiru.kerjaaja.presentation.profile.appearance.theme.datastore

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainActivityViewModel by viewModels()
    private val dialogError by lazy { DialogError() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mainViewModel.events.observe(this) { event ->
            when (event) {
                is MainActivityEvents.OnError -> showError(event.throwable)
                is MainActivityEvents.OnLogout -> onLogout()
                is MainActivityEvents.OnSuccess -> Unit
                is MainActivityEvents.ShowLoading -> showLoading()
            }
            when {
                event !is MainActivityEvents.ShowLoading &&
                event !is MainActivityEvents.OnLogout -> closeLoading()
            }
        }

        getThemeSetting()
    }

    private fun closeLoading() {
        binding.cpiCircularProgress.visibility = View.GONE
        binding.fcvMainNav.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.cpiCircularProgress.visibility = View.VISIBLE
        binding.fcvMainNav.visibility = View.GONE
    }

    private fun onLogout() {
        mainViewModel.logout()
    }

    private fun showError(throwable: Throwable) {
        dialogError.setMessage(throwable.message ?: "Terjadi kesalahan sistem")
        dialogError.showDialog(supportFragmentManager)
    }

    private fun getThemeSetting(){
        val bindingSetting = FragmentChangeThemeBinding.inflate(layoutInflater)
        val preference = SettingsThemePreferences.getInstance(datastore)
        val switchViewModel = ViewModelProvider(this, ViewModelFactory(preference))[ChangeThemeViewModel::class.java]
        switchViewModel.getThemeSettings().observe(this){ isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                bindingSetting.changeTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                bindingSetting.changeTheme.isChecked = false
            }
        }

        bindingSetting.changeTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            switchViewModel.saveThemeSetting(isChecked)

        }
    }


}