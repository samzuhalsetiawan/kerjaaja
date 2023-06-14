package com.animebiru.kerjaaja.presentation.ui.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentRegisterBinding
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.toGender
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.ui.activity.main.MainActivity
import com.animebiru.kerjaaja.presentation.viewmodels.UserViewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBindings(FragmentRegisterBinding::bind)
    private val userViewModel: UserViewModel by activityViewModels()
    private val mainActivity by lazy { activity as? MainActivity }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            etlPasswordConfirm.setupMatchingWithEditText(etlPassword)
            btnRegister.setupWithEditText(etlUsername, etlFullName, etlPassword, etlPasswordConfirm)
            btnRegister.setOnClickListener { onButtonRegisterClicked() }
        }

    }

    private fun onButtonRegisterClicked() {
        val username = binding.etUsername.text.toString()
        val fullName = binding.etFullName.text.toString()
        val password = binding.etPassword.text.toString()
        val gender = binding.acDropdownGender.text.toString().toGender() ?: run {
            mainActivity?.showError("Gender perlu diisi")
            return
        }
        userViewModel.register(username, fullName, gender, password)
    }
}