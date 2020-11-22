package com.missclick.fireassistant.ui.report.photoReview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.missclick.fireassistant.R

class PhotoReviewFragment : Fragment() {



    private lateinit var viewModel: PhotoReviewViewModel
    private var azimuth: Float? = null
    private var photo: ByteArray? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            azimuth = it.getFloat("azimuth")
            photo = it.getByteArray("photo")
        }
        return inflater.inflate(R.layout.photo_review_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotoReviewViewModel::class.java)
        // TODO: Use the ViewModel
    }


    companion object {
        fun newInstance(azimuth : Float, photo : ByteArray):Bundle{
            return Bundle().apply {
                putFloat("azimuth", azimuth)
                putByteArray("photo", photo)
            }
        }
    }

}