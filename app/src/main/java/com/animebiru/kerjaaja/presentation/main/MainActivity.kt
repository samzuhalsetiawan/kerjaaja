package com.animebiru.kerjaaja.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}