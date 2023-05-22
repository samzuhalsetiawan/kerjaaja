package com.animebiru.kerjaaja.domain.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.animebiru.kerjaaja.presentation.keamanan.KeamananFragment
import com.animebiru.kerjaaja.presentation.tampilan.TampilanFragment

class ProfileSectionsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){ //Belum masukin yang job karena masi agak bingung
            0 -> fragment = TampilanFragment()
            1 -> fragment = KeamananFragment()
        }
        return fragment as Fragment
    }
    override fun getItemCount(): Int {
        return 2 //Belum masukin return
    }



}