package com.animebiru.kerjaaja.domain.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KerjaAjaApp: Application() {

    companion object {
        const val BASE_URL = "https://kerjaaja-backend-production.up.railway.app/"
    }

}