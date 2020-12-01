package com.missclick.fireassistant.ui.myreports.deteils

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.missclick.fireassistant.R
import com.missclick.fireassistant.data.models.FireReportModel

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

    companion object {

        @JvmStatic
        fun newInstance(data: FireReportModel) : Bundle =
             Bundle().apply {
                putSerializable(ARG_PARAM1, data)
            }
    }
}