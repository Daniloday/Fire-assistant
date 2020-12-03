package com.missclick.fireassistant.ui.list.details

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.missclick.fireassistant.MainActivity
import com.missclick.fireassistant.R
import com.missclick.fireassistant.adapter.MyReportsAdapter
import com.missclick.fireassistant.data.models.FireModel
import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.ui.myreports.deteils.DetailsReportFragment
import kotlinx.android.synthetic.main.fragment_details_fire.*
import kotlinx.android.synthetic.main.my_reports_fragment.*


class DetailsFireFragment : Fragment() {

    private var fireModel : FireModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fireModel = it.getSerializable("fireModel") as FireModel
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_fire, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myLatitude = (fireModel?.coordinate?.x ?: 0)
        val myLongitude = (fireModel?.coordinate?.y ?: 0)

        coordinate_text.text = "$myLatitude,$myLongitude"

        coordinate_text.setOnLongClickListener {
            val clipboard: ClipboardManager? =
                (context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
            val clip = ClipData.newPlainText(android.R.attr.label.toString(), coordinate_text.text)
            clipboard!!.setPrimaryClip(clip)
            Toast.makeText(context,"Copied", Toast.LENGTH_SHORT).show()
            true
        }

        go_to_google_maps_btn.setOnClickListener {
            toMap(x = myLatitude as Double, y = myLongitude as Double)
        }

        val adapter = MyReportsAdapter{
            view.findNavController().navigate(R.id.detailsReportFragment, DetailsReportFragment.newInstance(it))
        }
        fireModel?.reports?.let { adapter.setData(it) }
        val layoutManager = GridLayoutManager(activity as MainActivity, 3)
        reports_recycler.adapter = adapter
        reports_recycler.layoutManager = layoutManager
    }

    private fun toMap(x : Double, y : Double){
        val coordinate =
            "geo:<$x>,<$y>?q=<$x>,<$y>(fire)"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(coordinate))
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance(data: FireModel) : Bundle =
            Bundle().apply {
                putSerializable("fireModel", data)
            }
    }


}