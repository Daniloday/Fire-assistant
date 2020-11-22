package com.missclick.fireassistant.ui.dashboard

import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.util.Log
import androidx.core.content.ContextCompat.checkSelfPermission
import java.util.jar.Manifest

class CameraService(val cameraManager: CameraManager, cameraID: String) {

    val  myCameras   = null

    var CAMERA1   = 0;
    var CAMERA2   = 1;




        var cameraID : String? = null
        var  cameraDevice : CameraDevice? = null
        var captureSession : CameraCaptureSession? = null


    fun isOpen() : Boolean {
            if (cameraDevice == null) {
                return false;
            } else {
                return true;
            }
        }


    fun openCamera() {
        try {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                    cameraManager.openCamera(cameraID, cameraCallback, null);
                }
        }
        catch (e: CameraAccessException) {
                Log.i("LOG_TAG", "error");

        }


    fun closeCamera() {
            if (cameraDevice != null) {
                cameraDevice!!.close();
                cameraDevice = null;
            }
    }


        val mCameraCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                cameraDevice = camera
                Log.i("LOG_TAG", "Open camera  with id:" + cameraDevice!!.id)
                createCameraPreviewSession()
            }

            override fun onDisconnected(camera: CameraDevice) {
                cameraDevice.close()
                Log.i("LOG_TAG", "disconnect camera  with id:" + (cameraDevice?.getId() ?: "error"))
                cameraDevice = null
            }

            override fun onError(camera: CameraDevice, error: Int) {
                Log.i("LOG_TAG", "error! camera id:" + camera.id + " error:" + error)
            }
        }

        fun createCameraPreviewSession() {

            val texture = imageView.getSurfaceTexture();
            // texture.setDefaultBufferSize(1920,1080);
            Surface surface = new Surface(texture);

            try {
                final CaptureRequest.Builder builder =
                        mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

                builder.addTarget(surface);
                mCameraDevice.createCaptureSession(Arrays.asList(surface),
                        new CameraCaptureSession.StateCallback() {

                            @Override
                            public void onConfigured(CameraCaptureSession session) {
                                mCaptureSession = session;
                                try {
                                    mCaptureSession.setRepeatingRequest(builder.build(),null,null);
                                } catch (CameraAccessException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onConfigureFailed(CameraCaptureSession session) { }}, null );
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

}