package com.missclick.fireassistant.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.missclick.fireassistant.R
import com.missclick.fireassistant.data.models.FireModel
import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.domain.Computation
import com.missclick.fireassistant.domain.Coordinate

class FireListAdapter(val callback : (FireModel) -> (Unit)): RecyclerView.Adapter<FireListAdapter.FireListViewHolder>() {

    private val reports = mutableListOf<FireModel>()
    var myCoordinate: Coordinate? = null

    fun setCoordinate(coordinate: Coordinate){
        myCoordinate = coordinate
    }

    fun setData(fireList : List<FireModel>){
        reports.clear()
        reports.addAll(fireList)
        notifyDataSetChanged()
    }

    fun sort(){
        reports.sortBy { myCoordinate?.let { it1 -> Computation.getRadius(firstCoordinate = it1, secondCoordinate = it.coordinate) } }
    }

    fun addReport(fireModel: FireModel){
        for(report in reports){
            if(report.coordinate.x == fireModel.coordinate.x
                && report.coordinate.y == fireModel.coordinate.y)
                return
        }
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
        private val image : ImageView = itemView.findViewById(R.id.warning_image)
        val root : CardView = itemView.findViewById(R.id.root)
        val description : TextView = itemView.findViewById(R.id.description_fire)
        val kilometres : TextView = itemView.findViewById(R.id.kilometres)

        fun bind(model : FireModel){
            if(model.reports.size < 3) {
                image.setImageResource(R.drawable.warning)
                description.text = "Be careful, less than 3 report"
            }
            else if(model.reports.size < 5) {
                image.setImageResource(R.drawable.danger)
                description.text = "Less than 5 report"
            }
            else {
                image.setImageResource(R.drawable.confirm)
                description.text = "More than 5 report"
            }
            root.setOnClickListener {
                callback.invoke(model)
            }
            kilometres.text = (myCoordinate?.let { Computation.getRadius(firstCoordinate = it, secondCoordinate = model.coordinate) }
                ?.times(111)).toString()
        }
    }

}