package com.missclick.fireassistant.ui.myreports.deteils

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.missclick.fireassistant.R
import com.missclick.fireassistant.data.models.FireReportModel
import kotlinx.android.synthetic.main.fragment_details_report.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "image"

class DetailsReportFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var fireReportModel : FireReportModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fireReportModel = it.getSerializable(ARG_PARAM1) as FireReportModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        report_image_detail.setImageBitmap(fireReportModel?.photo?.size?.let {
            BitmapFactory.decodeByteArray(fireReportModel?.photo, 0,
                it
            )
        })
        report_info_detail.text = "latitude : ${fireReportModel?.latitude.toString()} " +
                "longitude :  ${fireReportModel?.longitude.toString()}"
    }

    companion object {

        @JvmStatic
        fun newInstance(data: FireReportModel) : Bundle =
             Bundle().apply {
                putSerializable(ARG_PARAM1, data)
            }
    }
}