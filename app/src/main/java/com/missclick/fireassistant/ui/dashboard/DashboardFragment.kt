package com.missclick.fireassistant.ui.dashboard

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.missclick.fireassistant.MainActivity
import com.missclick.fireassistant.R
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment(), SensorEventListener {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var sensorManager: SensorManager? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        sensorManager = ((activity as MainActivity).getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager)
        val accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION) //TYPE_ORIENTATION
        sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)

        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    private var x = 0f
    private var y = 0f
    private var z = 0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {  it.findNavController().navigate(R.id.cameraFragment)}
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) { //Изменение точности показаний датчика
    }

    override fun onSensorChanged(event: SensorEvent?) {
        x = (event!!.values[0] ).toFloat() //Плоскость XY
        y = (event!!.values[1]).toFloat() //Плоскость XZ
        z = (event!!.values[2]).toFloat(); //Плоскость ZY
        Log.e("x",x.toString())
        Log.e("y",y.toString())
        Log.e("z",z.toString())
    }
}