package com.missclick.fireassistant.ui.dashboard

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.camerakit.CameraKitView
import com.missclick.fireassistant.MainActivity
import com.missclick.fireassistant.R
import kotlinx.android.synthetic.main.camera_fragment.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class CameraFragment : Fragment(), SensorEventListener {

    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var cameraKitView: CameraKitView
    private var sensorManager: SensorManager? = null
    private var x = 0f
    private var y = 0f
    private var z = 0f

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
        sensorManager = ((activity as MainActivity).getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager)
        val accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION) //TYPE_ORIENTATION
        sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)

        cameraKitView = camera
        btn.setOnClickListener {
            cameraKitView.captureImage { cameraKitView, capturedImage ->
                Log.e("callback", "works")
                val savedPhoto = File(Environment.getExternalStorageDirectory(), "photo.jpg")
                try {
                    val outputStream = FileOutputStream(savedPhoto.getPath())
                    outputStream.write(capturedImage)
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val bitmap = BitmapFactory.decodeByteArray(capturedImage, 0, capturedImage.size)
                kekWImage.setImageBitmap(bitmap)
            }
        }
    }

    override fun onAccuracyChanged(
            sensor: Sensor?,
            accuracy: Int
    ) { //Изменение точности показаний датчика
    }

    override fun onSensorChanged(event: SensorEvent?) {
        z = (event!!.values[0] ).toFloat() //Azimuth
        x = (event.values[1]).toFloat() //Pitch
        y = (event.values[2]).toFloat(); //Roll
        Log.e("x", x.toString())
        Log.e("y", y.toString())
        Log.e("z", z.toString())
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