package com.example.list11210318

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class SiteAdapter(private val mList: ArrayList<DataSite>) : RecyclerView.Adapter<SiteAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mytvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val mytvCounty: TextView = itemView.findViewById(R.id.tvCounty)
        val mytvValue1: TextView = itemView.findViewById(R.id.tvValue1)
        val mytvValue2: TextView = itemView.findViewById(R.id.tvValue2)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val context: Context = itemView.context
                    val item = mList[position]
                    Toast.makeText(
                        context,
                        "點擊項目：${item.item1} / ${item.item2} / ${item.value1} / ${item.value2}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.myitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val oneItem = mList[position]
        holder.mytvTitle.text = oneItem.item1
        holder.mytvCounty.text = oneItem.item2
        holder.mytvValue1.text = oneItem.value1
        holder.mytvValue2.text = oneItem.value2
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}
