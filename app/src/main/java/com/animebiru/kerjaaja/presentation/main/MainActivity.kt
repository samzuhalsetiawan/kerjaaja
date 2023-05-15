package com.animebiru.kerjaaja.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation.AnimationListener
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.ActivityMainBinding
import com.google.android.material.search.SearchView
import com.google.android.material.search.SearchView.TransitionState

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fcvMainNav) as NavHostFragment
        navController = navHost.navController

        binding.bnvMainBottomNavigation.setupWithNavController(navController)

        binding.svMainSearchView.addTransitionListener { searchView, previousState, newState ->
            when (newState) {
                TransitionState.SHOWING -> binding.fabCreateJob.hide()
                TransitionState.HIDDEN -> binding.fabCreateJob.show()
                else -> Unit
            }
        }

        
    }


}