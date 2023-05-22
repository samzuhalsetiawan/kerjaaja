package com.animebiru.kerjaaja.presentation.tampilan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.domain.adapter.TampilanAdapter


class TampilanFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var tampilanAdapter: TampilanAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemList = listOf(
            "Ganti Foto Profil",
            "Ganti Username",
            "Ganti Tema Aplikasi")
        tampilanAdapter = TampilanAdapter(itemList)
        recyclerView = view.findViewById(R.id.rvTampilan)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = tampilanAdapter


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tampilan, container, false)
    }

    companion object {

    }
}