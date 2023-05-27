package com.animebiru.kerjaaja.presentation.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.data.models.Chat
import com.animebiru.kerjaaja.databinding.FragmentChatBinding
import com.animebiru.kerjaaja.domain.adapter.ChatAdapter
import com.animebiru.kerjaaja.domain.utils.HelperDummyData
import com.animebiru.kerjaaja.domain.utils.viewBindings

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val binding by viewBindings(FragmentChatBinding::bind)
    private val appBarConfiguration by lazy { AppBarConfiguration(setOf(R.id.homeFragment, R.id.historyFragment, R.id.profileFragment)) }
    private val chatAdapter by lazy { ChatAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mtbChatPage.setupWithNavController(findNavController(), appBarConfiguration)
        binding.rvChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        chatAdapter.listOfChat = HelperDummyData.dummyChat
    }

}