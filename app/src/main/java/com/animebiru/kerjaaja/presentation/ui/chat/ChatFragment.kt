package com.animebiru.kerjaaja.presentation.ui.chat

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.domain.models.Chat
import com.animebiru.kerjaaja.data.sources.remote.SocketIO
import com.animebiru.kerjaaja.databinding.FragmentChatBinding
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.repeatCoroutineWhenStarted
import com.animebiru.kerjaaja.presentation.adapter.ChatAdapter
import com.animebiru.kerjaaja.domain.utils.HelperDummyData
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.viewmodels.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID


@AndroidEntryPoint
class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val binding by viewBindings(FragmentChatBinding::bind)
    private val chatViewModel: ChatViewModel by viewModels()
    private val appBarConfiguration by lazy { AppBarConfiguration(setOf(R.id.homeFragment, R.id.historyFragment, R.id.profileFragment)) }
    private val chatAdapter by lazy { ChatAdapter() }
    private val args: ChatFragmentArgs by navArgs()
    private val receiverId by lazy { args.receiverId }
    private val socket by lazy { SocketIO.socket }
    private var originalMode : Int? = null
    private var userId: String? = null

    override fun onStart() {
        super.onStart()
        originalMode = activity?.window?.attributes?.softInputMode
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        socket.on(Socket.EVENT_CONNECT, onConnect)
        socket.on(Socket.EVENT_DISCONNECT, onDisconnect)
        socket.connect()

    }

    override fun onDestroy() {
        super.onDestroy()

        socket.disconnect()
        socket.off(Socket.EVENT_CONNECT, onConnect)
        socket.off(Socket.EVENT_DISCONNECT, onDisconnect)
        originalMode?.let { activity?.window?.setSoftInputMode(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mtbChatPage.setupWithNavController(findNavController(), appBarConfiguration)
        binding.rvChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.btnSend.setOnClickListener {
            val userId = this.userId ?: return@setOnClickListener
            val message = binding.etChatInput.text.toString()
            val payLoad = buildJsonObject {
                put("receiver", receiverId)
                put("sender", userId)
                put("message", message)
            }
            Log.d("MY_DEBUG:${this@ChatFragment.javaClass.simpleName}", "onViewCreated: $payLoad")
            socket.emit("sendMessage", payLoad)
            binding.etChatInput.text?.clear()
            binding.etChatInput.clearFocus()
            Log.d("MY_DEBUG:${this@ChatFragment.javaClass.simpleName}", "onViewCreated: message sanded")
            val chat = Chat(message, false, System.currentTimeMillis().toInt())
            chatAdapter.listOfChat = chatAdapter.listOfChat + listOf(chat)
        }

    }

    override fun onDestroyView() {
        userId?.let {
            val payLoad = buildJsonObject {
                put("receiver", receiverId)
                put("sender", it)
            }
            socket.emit("messageRead", payLoad)
        }
        super.onDestroyView()
    }

    private val onConnect = Emitter.Listener {
        Log.d("MY_DEBUG:${this@ChatFragment.javaClass.simpleName}", "onViewCreated: Connected")
        repeatCoroutineWhenStarted {
            userId = chatViewModel.userId.first()
            socket.emit("userConnected", userId);

            val payLoad = buildJsonObject {
                put("receiver", receiverId)
                put("sender", userId)
            }
            socket.emit("getMessages", payLoad, onGetMessages)
            Log.d("MY_DEBUG:${this@ChatFragment.javaClass.simpleName}", "onViewCreated: socket init finish")
        }
    }

    private val onGetMessages = Ack { data ->
        for (any in data) {
            Log.d("MY_DEBUG:${this@ChatFragment.javaClass.simpleName}", "onGetMessage: it -> $any")
        }
        val messages = data[0] as JSONArray
        val listOfChat = List(messages.length()) {
            val jsonMessage = messages.getJSONObject(it)
            val isIncoming = jsonMessage.has("receiver") && jsonMessage.getString("receiver") == receiverId
            Chat(jsonMessage.getString("message"), isIncoming, jsonMessage.getInt("timestamp"))
        }
        lifecycleScope.launch(Dispatchers.Main) {
            chatAdapter.listOfChat = listOfChat
        }
        val payLoad = buildJsonObject {
            put("receiver", receiverId)
            put("sender", userId)
        }
        socket.emit("messageRead", payLoad)
    }

    private val onDisconnect = Emitter.Listener {
        Log.d("MY_DEBUG:${this@ChatFragment.javaClass.simpleName}", "onViewCreated: disconnect")
    }
}