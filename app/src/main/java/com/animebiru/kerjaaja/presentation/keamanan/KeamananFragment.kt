package com.animebiru.kerjaaja.presentation.keamanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.domain.adapter.KeamananAdapter



class KeamananFragment : Fragment() {
    private lateinit var keamananAdapter: KeamananAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemList = listOf(
            "Ganti Password",
            "Notifikasi",
            "Hapus Akun")
        keamananAdapter = KeamananAdapter(itemList)
        recyclerView = view.findViewById(R.id.rvKeamanan)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = keamananAdapter

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keamanan, container, false)
    }

    companion object {
    }
}