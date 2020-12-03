package com.missclick.fireassistant.ui.list.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.missclick.fireassistant.R
import com.missclick.fireassistant.data.models.FireModel
import com.missclick.fireassistant.data.models.FireReportModel
import kotlinx.android.synthetic.main.fragment_details_fire.*


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myLatitude = (fireModel?.coordinate?.x ?: 0)
        val myLongitude = (fireModel?.coordinate?.y ?: 0)
        val coordinate =
            "geo:<$myLatitude>,<$myLongitude>?q=<$myLatitude>,<$myLongitude>(fire)"
        coordinate_text.text = coordinate
        go_to_google_maps_btn.setOnClickListener {
//            val coordinate = "map:" + (fireModel?.coordinate?.x ?: 0).toString() + "," + (fireModel?.coordinate?.y
//                ?: 0).toString()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(coordinate))
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(data: FireModel) : Bundle =
            Bundle().apply {
                putSerializable("fireModel", data)
            }
    }


}