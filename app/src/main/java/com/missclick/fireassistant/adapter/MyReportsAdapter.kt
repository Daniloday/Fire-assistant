package com.missclick.fireassistant.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.missclick.fireassistant.R
import com.missclick.fireassistant.data.models.FireReportModel

class MyReportsAdapter(val callback : (FireReportModel) -> (Unit)) : RecyclerView.Adapter<MyReportsAdapter.MyReportViewHolder>() {

    private val reports = mutableListOf<FireReportModel>()

    fun setData(newReports : List<FireReportModel>){
        reports.clear()
        reports.addAll(newReports)
        notifyDataSetChanged()
    }

    fun addReport(fireReportModel: FireReportModel){
        reports.add(fireReportModel)
        notifyItemInserted(reports.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReportsAdapter.MyReportViewHolder {
        return MyReportViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.report_list_item, parent, false))
    }

    override fun getItemCount(): Int = reports.size
    override fun onBindViewHolder(holder: MyReportsAdapter.MyReportViewHolder, position: Int) {
        holder.bind(model = reports[position])
    }

    inner class MyReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val image : ImageView = itemView.findViewById(R.id.report_image)
        fun bind(model : FireReportModel){
            image.setImageBitmap(BitmapFactory.decodeByteArray(model.photo, 0, model.photo.size))
            image.setOnClickListener {
                callback.invoke(model)
            }
        }
    }

}