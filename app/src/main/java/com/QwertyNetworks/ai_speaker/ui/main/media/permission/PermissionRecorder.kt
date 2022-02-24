package com.QwertyNetworks.ai_speaker.ui.main.media.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionRecorder(var context: Context?) {

    fun checkPermissions() : Boolean{
        val firstPermission = ActivityCompat.checkSelfPermission(
            context!!, Manifest.permission.RECORD_AUDIO
        )
        val secondPermission = ActivityCompat.checkSelfPermission(
            context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val thirstPermission = ActivityCompat.checkSelfPermission(
            context!!, Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val cameraPermission = ActivityCompat.checkSelfPermission(
            context!!, Manifest.permission.CAMERA
        )
        return firstPermission == PackageManager.PERMISSION_GRANTED &&
                secondPermission == PackageManager.PERMISSION_GRANTED &&
                thirstPermission == PackageManager.PERMISSION_GRANTED &&
                cameraPermission == PackageManager.PERMISSION_GRANTED
    }
}