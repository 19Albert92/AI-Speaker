package com.QwertyNetworks.ai_speaker.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.QwertyNetworks.ai_speaker.MainActivity
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import java.util.*

class MyStartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_start)

        //если я захожу не в первый раз
        if(savedInstanceState == null) {
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }, 2500)
        }
    }
}