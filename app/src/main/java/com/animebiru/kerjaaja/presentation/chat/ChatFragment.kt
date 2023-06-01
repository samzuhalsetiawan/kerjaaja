package com.animebiru.kerjaaja.presentation.chat

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.data.models.Chat
import com.animebiru.kerjaaja.data.sources.remote.SocketIO
import com.animebiru.kerjaaja.databinding.FragmentChatBinding
import com.animebiru.kerjaaja.domain.adapter.ChatAdapter
import com.animebiru.kerjaaja.domain.utils.HelperDummyData
import com.animebiru.kerjaaja.domain.utils.viewBindings
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.util.UUID


class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val binding by viewBindings(FragmentChatBinding::bind)
    private val appBarConfiguration by lazy { AppBarConfiguration(setOf(R.id.homeFragment, R.id.historyFragment, R.id.profileFragment)) }
    private val chatAdapter by lazy { ChatAdapter() }
    private val socket by lazy { SocketIO.socket }
    private var originalMode : Int? = null

    override fun onStart() {
        super.onStart()
        originalMode = activity?.window?.attributes?.softInputMode
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onDestroy() {
        super.onDestroy()
        originalMode?.let { activity?.window?.setSoftInputMode(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mtbChatPage.setupWithNavController(findNavController(), appBarConfiguration)
        binding.rvChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        chatAdapter.listOfChat = HelperDummyData.dummyChat

        binding.btnSend.setOnClickListener {

        }

        binding.etChatInput.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {

                    true
                }
                else -> false
            }
        }

    }
}