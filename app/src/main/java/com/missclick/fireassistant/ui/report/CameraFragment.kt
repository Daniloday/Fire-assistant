package com.missclick.fireassistant.ui.report

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.camerakit.CameraKitView
import com.google.android.gms.location.*
import com.missclick.fireassistant.MainActivity
import com.missclick.fireassistant.R
import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.ui.report.photoReview.PhotoReviewFragment
import kotlinx.android.synthetic.main.camera_fragment.*


class CameraFragment : Fragment(), SensorEventListener{

    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var cameraKitView: CameraKitView
    private var sensorManager: SensorManager? = null
    private var locationManager: LocationManager? = null
    private var x = 0f
    private var y = 0f
    private var z = 0f
    private var longitude = 0.0
    private var latitude = 0.0


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.camera_fragment, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraViewModel =
                ViewModelProvider(this).get(CameraViewModel::class.java)
        sensorManager = (activity as MainActivity).getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION) //TYPE_ORIENTATION
        sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)

        locationManager = (activity as MainActivity).getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        cameraInit()
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



    }

    fun cameraInit(){
        cameraKitView = camera
        cameraKitView.setPermissionsListener(object : CameraKitView.PermissionsListener{
            override fun onPermissionsSuccess() {
            }

            override fun onPermissionsFailure() {
                (activity as MainActivity).sosiNogu()
            }

        })
        btn.setOnClickListener {
            cameraKitView.captureImage { _, capturedImage ->
                Log.e("s", "se")
                it.findNavController().navigate(R.id.navigation_review, PhotoReviewFragment
                    .newInstance(data = FireReportModel(photo = capturedImage, azimuth = x, longitude = longitude, latitude = latitude)))
            }
        }
    }

    override fun onAccuracyChanged(
            sensor: Sensor?,
            accuracy: Int
    ) { //Изменение точности показаний датчика
    }

    override fun onSensorChanged(event: SensorEvent?) {
        z = (event!!.values[0] )//Azimuth
        x = (event.values[1]) //Pitch
        y = (event.values[2]) //Roll
    }

    override fun onStart() {
        super.onStart()
        cameraKitView.onStart()
    }

    override fun onResume() {
        super.onResume()
        cameraKitView.onResume()
        
    }

    override fun onPause() {
        cameraKitView.onPause()
        super.onPause()
    }

    override fun onStop() {
        cameraKitView.onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

}






