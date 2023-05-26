package com.animebiru.kerjaaja.presentation.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation.AnimationListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.ActivityMainBinding
import com.animebiru.kerjaaja.domain.sealed_class.MainActivityEvents
import com.animebiru.kerjaaja.presentation.dialog.DialogError
import com.google.android.material.search.SearchView
import com.google.android.material.search.SearchView.TransitionState

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


}