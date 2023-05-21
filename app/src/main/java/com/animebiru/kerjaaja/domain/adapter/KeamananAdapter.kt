package com.animebiru.kerjaaja.domain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.R

class KeamananAdapter(private val itemList: List<String>): RecyclerView.Adapter<KeamananAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.itemSetting)

        fun bind(item: String) {
            titleTextView.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_tampilan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return minOf(itemList.size,3)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }
}