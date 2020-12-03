package com.missclick.fireassistant.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.missclick.fireassistant.R
import com.missclick.fireassistant.data.models.FireModel
import com.missclick.fireassistant.data.models.FireReportModel

class FireListAdapter(val callback : (FireModel) -> (Unit)): RecyclerView.Adapter<FireListAdapter.FireListViewHolder>() {

    private val reports = mutableListOf<FireModel>()

    fun setData(fireList : List<FireModel>){
        reports.clear()
        reports.addAll(fireList)
        notifyDataSetChanged()
    }

    fun addReport(fireModel: FireModel){
        reports.add(fireModel)
        notifyItemInserted(reports.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FireListAdapter.FireListViewHolder {
        return FireListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fires_list_item, parent, false))
    }

    override fun getItemCount(): Int = reports.size
    override fun onBindViewHolder(holder: FireListAdapter.FireListViewHolder, position: Int) {
        holder.bind(model = reports[position])
    }

    inner class FireListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val image : ImageView = itemView.findViewById(R.id.image_item_fire)
        fun bind(model : FireModel){
            image.setImageBitmap(BitmapFactory.decodeByteArray(model.reports[0].photo, 0, model.reports[0].photo.size))
            image.setOnClickListener {
                callback.invoke(model)
            }
        }
    }

}