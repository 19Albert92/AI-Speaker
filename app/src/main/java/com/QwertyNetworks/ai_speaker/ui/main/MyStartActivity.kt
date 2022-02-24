package com.QwertyNetworks.ai_speaker.ui.main

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.QwertyNetworks.ai_speaker.MainActivity
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.databinding.ActivityMyStartBinding
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MyStartActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //если я захожу не в первый раз
        if(savedInstanceState == null) {
//            Handler().postDelayed({
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            }, 2500)

            CoroutineScope(Dispatchers.Main).launch {

                binding.progress.max = 1000

                var value = 1000

                ObjectAnimator.ofInt(binding.progress, "progress", value).setDuration(2000).start()

                delay(2000) //ждать 2 минуты будет
                startActivity(Intent(this@MyStartActivity, MainActivity::class.java))
            }

        }
    }
}