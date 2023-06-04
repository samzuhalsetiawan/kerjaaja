package com.animebiru.kerjaaja.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.domain.models.Chat

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val bubbleChat: TextView = view.findViewById(R.id.tvChatBubble)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listOfChat: List<Chat>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemViewType(position: Int): Int {
        return if (listOfChat[position].isIncoming) TYPE_MESSAGE_INCOMING else TYPE_MESSAGE_OUTGOING
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                if (viewType == TYPE_MESSAGE_INCOMING) R.layout.bubble_chat_incoming else R.layout.bubble_chat_outgoing,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bubbleChat.text = listOfChat[position].message
    }

    override fun getItemCount(): Int = listOfChat.size

    companion object {
        private const val TYPE_MESSAGE_INCOMING = 1
        private const val TYPE_MESSAGE_OUTGOING = 0
    }
}