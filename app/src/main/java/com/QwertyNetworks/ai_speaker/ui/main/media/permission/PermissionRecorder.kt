package com.QwertyNetworks.ai_speaker.ui.main.media.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat


class PermissionRecorder(var context: Context?) {

    fun checkPermissions() : Boolean{
        val firstPermisson = ActivityCompat.checkSelfPermission(
            context!!, Manifest.permission.RECORD_AUDIO
        )
        val secondPermisson = ActivityCompat.checkSelfPermission(
            context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return firstPermisson == PackageManager.PERMISSION_GRANTED &&
                secondPermisson == PackageManager.PERMISSION_GRANTED
    }
}