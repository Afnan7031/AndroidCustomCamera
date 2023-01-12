package com.example.customcamera.helpers

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.camera.core.Camera
import androidx.camera.core.FocusMeteringAction
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture

object CameraUtils {
    var camera: Camera? = null

    fun enableTorch(context: Context, enable: Boolean) {
        // Getting the CameraControl instance from the camera
        val cameraControl = camera?.cameraControl
        // Use the CameraControl instance to enable the torch
        val enableTorchLF: ListenableFuture<Void>? = cameraControl?.enableTorch(enable)
        enableTorchLF?.addListener({
            try {
                enableTorchLF.get()
                // At this point, the torch has been successfully enabled
            } catch (exception: Exception) {
                // Handle any potential errors
            }
        }, ContextCompat.getMainExecutor(context))

    }

    fun tabToFocus(previewView : PreviewView, xCordinates : Float, yCordinates : Float){
        // Get the MeteringPointFactory from PreviewView
        val factory =previewView.meteringPointFactory

        // Create a MeteringPoint from the tap coordinates
        val point = factory.createPoint(xCordinates, yCordinates)

        // Create a MeteringAction from the MeteringPoint, you can configure it to specify the metering mode
        val action = FocusMeteringAction.Builder(point).build()

        // Trigger the focus and metering. The method returns a ListenableFuture since the operation
        // is asynchronous. You can use it get notified when the focus is successful or if it fails.
        camera?.cameraControl?.startFocusAndMetering(action)
    }
}