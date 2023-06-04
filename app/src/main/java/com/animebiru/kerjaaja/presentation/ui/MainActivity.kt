package com.animebiru.kerjaaja.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.ActivityMainBinding
import com.animebiru.kerjaaja.domain.events.AuthenticationEvent
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.ui.dialog.DialogError
import com.animebiru.kerjaaja.presentation.ui.register.RegisterFragmentDirections
import com.animebiru.kerjaaja.presentation.viewmodels.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBindings(ActivityMainBinding::bind)
    private val authenticationViewModel: AuthenticationViewModel by viewModels()
    private val dialogError by lazy { DialogError() }
    private val navHost by lazy { supportFragmentManager.findFragmentById(R.id.fcvMainNav) as NavHostFragment }
    private val navController by lazy { navHost.navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authenticationViewModel.events.observe(this) { event ->
            Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "onCreate: event: ${event::class.java.simpleName}")
            when (event) {
                is AuthenticationEvent.OnError -> showError(event.errorMessage)
                is AuthenticationEvent.OnLoading -> showLoading()
                is AuthenticationEvent.OnLogin -> redirectToHomePage()
                is AuthenticationEvent.OnLogout -> redirectToLoginPage()
                is AuthenticationEvent.OnRegisterSuccess -> redirectToLoginPage(event.username)
            }
        }
    }

    private fun redirectToLoginPage(username: String? = null) {
        if (username == null) {
            val navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
            navController.navigate(R.id.loginFragment, Bundle(), navOptions)
        } else {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(username)
            navController.navigate(action)
        }
        closeLoading()
    }

    private fun redirectToHomePage() {
        navController.navigate(R.id.action_loginFragment_to_homeFragment)
        closeLoading()
    }

    private fun closeLoading() {
        binding.cpiCircularProgress.visibility = View.GONE
        binding.fcvMainNav.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.cpiCircularProgress.visibility = View.VISIBLE
        binding.fcvMainNav.visibility = View.GONE
    }

    fun showError(errorMessage: String) {
        dialogError.setMessage(errorMessage)
        dialogError.showDialog(supportFragmentManager)
        closeLoading()
    }


}