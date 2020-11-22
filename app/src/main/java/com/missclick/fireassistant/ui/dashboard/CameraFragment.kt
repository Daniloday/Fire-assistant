package com.missclick.fireassistant.ui.dashboard

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.missclick.fireassistant.MainActivity
import com.missclick.fireassistant.R


class CameraFragment : Fragment() {

    companion object {
        fun newInstance() = CameraFragment()
    }

    private lateinit var viewModel: CameraViewModel
    val LOG_TAG = "myLogs"
    var myCameras: Array<String>? = null
    private var mCameraManager: CameraManager? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        if ((activity as MainActivity).checkSelfPermission(Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED
            ||
            ContextCompat.checkSelfPermission(
                    (activity as MainActivity),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                    arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), 1
            )
        }
        return inflater.inflate(R.layout.camera_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCameraManager = ((activity as MainActivity).getSystemService(Context.CAMERA_SERVICE) as CameraManager?)
        try{
            // Получение списка камер с устройства

//            myCameras = new String [mCameraManager.getCameraIdList().length];

            // выводим информацию по камере
            for ( cameraID in mCameraManager!!.cameraIdList) {
                Log.i(LOG_TAG, "cameraID: " + cameraID)
                val id = Integer.parseInt(cameraID)

                // Получениe характеристик камеры
                val cc = mCameraManager!!.getCameraCharacteristics(cameraID)
                // Получения списка выходного формата, который поддерживает камера
                val configurationMap = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

                //  Определение какая камера куда смотрит
                val Faceing = cc.get(CameraCharacteristics.LENS_FACING)

                if (Faceing ==  CameraCharacteristics.LENS_FACING_FRONT)
                {
                    Log.i(LOG_TAG, "Camera with ID: " + cameraID + "  is FRONT CAMERA  ");
                }

                if (Faceing ==  CameraCharacteristics.LENS_FACING_BACK)
                {
                    Log.i(LOG_TAG, "Camera with: ID " + cameraID + " is BACK CAMERA  ");
                }


                // Получения списка разрешений которые поддерживаются для формата jpeg
                val sizesJPEG = configurationMap?.getOutputSizes(ImageFormat.JPEG)

                if (sizesJPEG != null) {
                    for (item in sizesJPEG) {
                        Log.i(LOG_TAG, "w:" + item.getWidth() + " h:" + item.getHeight())
                    }
                }  else {
                    Log.i(LOG_TAG, "camera don`t support JPEG")
                }
            }
        }
        catch (e: CameraAccessException){
//            Log.e(LOG_TAG, e.getMessage())
            e.printStackTrace()
        }

        mCameraManager = (activity as MainActivity).getSystemService(Context.CAMERA_SERVICE) as CameraManager?
        try {

            // Получение списка камер с устройства
            myCameras = arrayOf(arrayOfNulls<CameraService>(mCameraManager!!.cameraIdList.size).toString())
            for (cameraID in mCameraManager!!.cameraIdList) {
                Log.i(LOG_TAG, "cameraID: $cameraID")
                val id = cameraID.toInt()

                // создаем обработчик для камеры
//                myCameras.get(id) = CameraService(mCameraManager!!, cameraID)
            }
        } catch (e: CameraAccessException) {
            Log.e(LOG_TAG, e.message!!)
            e.printStackTrace()
        }

    }

}