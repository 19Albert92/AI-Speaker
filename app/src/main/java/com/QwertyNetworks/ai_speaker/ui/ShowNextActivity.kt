package com.QwertyNetworks.ai_speaker.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.QwertyNetworks.ai_speaker.UsesCase.webview.WebViewAgreement
import com.QwertyNetworks.ai_speaker.databinding.ActivityShowNextBinding


class ShowNextActivity : AppCompatActivity() {

   private lateinit var binding: ActivityShowNextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowNextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialButtons()
    }

    private fun initialButtons() {

        binding.checkboxRegister.setOnCheckedChangeListener { buttonView, isChecked ->

            if (!isChecked) {
                binding.mainButton.alpha = .5F
                binding.mainButton.isClickable = false
            } else {
                binding.mainButton.alpha = 1F
                binding.mainButton.setOnClickListener {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        binding.docUserAgreement.setOnClickListener {
            val intent = Intent(this, ShowWebAgreements::class.java)
            startActivity(intent)
        }
    }
}