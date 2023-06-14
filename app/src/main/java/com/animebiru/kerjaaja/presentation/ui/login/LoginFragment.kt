package com.animebiru.kerjaaja.presentation.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentLoginBinding
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.viewmodels.AuthenticationViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBindings(FragmentLoginBinding::bind)
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogin.setupWithEditText(etlUsername, etlPassword)
            btnLogin.setOnClickListener { onButtonLoginClicked() }
            btnRegister.setOnClickListener { onButtonRegisterClicked() }
        }

        setAnimation()

    }

    private fun onButtonLoginClicked() {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        authenticationViewModel.login(username, password)
    }

    private fun onButtonRegisterClicked() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun setAnimation(){

        val imgLogo = ObjectAnimator.ofFloat(binding.imgLogo,View.ALPHA,1f).setDuration(500)
        val textLogin = ObjectAnimator.ofFloat(binding.tvTitleLogin,View.ALPHA,1f).setDuration(500)
        val usernameField = ObjectAnimator.ofFloat(binding.etlUsername,View.ALPHA,1f).setDuration(500)
        val passwordField = ObjectAnimator.ofFloat(binding.etlPassword,View.ALPHA,1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin,View.ALPHA,1f).setDuration(500)
        val btnRegister = ObjectAnimator.ofFloat(binding.btnRegister,View.ALPHA,1f).setDuration(500)
        val labelRegister = ObjectAnimator.ofFloat(binding.tvLabelRegister, View.ALPHA,1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(imgLogo,textLogin,usernameField,passwordField,btnLogin,btnRegister,labelRegister)
            start()
        }
    }

}