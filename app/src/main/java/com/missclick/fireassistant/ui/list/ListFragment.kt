package com.missclick.fireassistant.ui.list

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.missclick.fireassistant.MainActivity
import com.missclick.fireassistant.R
import com.missclick.fireassistant.adapter.FireListAdapter
import com.missclick.fireassistant.adapter.MyReportsAdapter
import com.missclick.fireassistant.domain.Coordinate
import com.missclick.fireassistant.ui.list.details.DetailsFireFragment
import com.missclick.fireassistant.ui.myreports.deteils.DetailsReportFragment
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.android.synthetic.main.my_reports_fragment.*
import kotlinx.android.synthetic.main.my_reports_fragment.my_reports_recycler

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private var locationManager: LocationManager? = null
    private var longitude = 0.0
    private var latitude = 0.0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        listViewModel =
                ViewModelProvider(this).get(ListViewModel::class.java)
        return inflater.inflate(R.layout.list_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel.getList(Coordinate(x = longitude,y = latitude))
        val request = LocationRequest()
        request.interval = 10000
        request.fastestInterval = 5000
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val permission = ContextCompat.checkSelfPermission((activity as MainActivity), Manifest.permission.ACCESS_FINE_LOCATION
        )
        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity as MainActivity)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location: Location? = locationResult.lastLocation
                    if (location != null) {
                        longitude = location!!.longitude
                        latitude = location!!.latitude
                    }
                }
            }, null)
        }
        val adapter = FireListAdapter{
            view.findNavController().navigate(R.id.detailsFireFragment, DetailsFireFragment.newInstance(it))
        }
        val layoutManager = LinearLayoutManager(activity as MainActivity)
        recycleFires.adapter = adapter
        recycleFires.layoutManager = layoutManager
        listViewModel.myReports.observe(viewLifecycleOwner) {
            Log.e("MyReport", it.toString())
            adapter.addReport(it)
//            adapter.sort(Coordinate(x = latitude, y = longitude))
        }
    }
}