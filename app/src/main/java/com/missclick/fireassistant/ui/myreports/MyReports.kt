package com.missclick.fireassistant.ui.myreports

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.missclick.fireassistant.MainActivity
import com.missclick.fireassistant.R
import com.missclick.fireassistant.adapter.MyReportsAdapter
import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.ui.myreports.deteils.DetailsReportFragment
import kotlinx.android.synthetic.main.my_reports_fragment.*

class MyReports : Fragment() {

    companion object {
        fun newInstance() = MyReports()
    }

    private lateinit var viewModel: MyReportsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_reports_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyReportsViewModel::class.java)
        val adapter = MyReportsAdapter{
            view.findNavController().navigate(R.id.detailsReportFragment, DetailsReportFragment.newInstance(it))
        }
        viewModel.myReports.observe(viewLifecycleOwner) {
            Log.e("MyReport", it.toString())
            adapter.addReport(it)
        }
        val layoutManager = GridLayoutManager(activity as MainActivity, 5)
        my_reports_recycler.adapter = adapter
        my_reports_recycler.layoutManager = layoutManager
        viewModel.getMyReporst()

    }


}