package com.missclick.fireassistant.ui.report.photoReview

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.missclick.fireassistant.R
import com.missclick.fireassistant.data.models.FireReportModel
import kotlinx.android.synthetic.main.photo_review_fragment.*

class PhotoReviewFragment : Fragment() {

    private lateinit var viewModel: PhotoReviewViewModel
    private var data : FireReportModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            data = it.getSerializable("data") as FireReportModel
        }
        return inflater.inflate(R.layout.photo_review_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotoReviewViewModel::class.java)
        val photoBitmap = BitmapFactory.decodeByteArray(data?.photo, 0, data?.photo!!.size)
        photo_imageView.setImageBitmap(photoBitmap)
        next_button.setOnClickListener {
//            it.findNavController().navigate()
            viewModel.fireReport(fireReportModel = data!!)
        }
    }


    companion object {
        fun newInstance(data : FireReportModel):Bundle{
            return Bundle().apply {
                putSerializable("data",data)
            }
        }
    }

}