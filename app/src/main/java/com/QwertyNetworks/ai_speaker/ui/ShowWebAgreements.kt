package com.QwertyNetworks.ai_speaker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.QwertyNetworks.ai_speaker.UsesCase.webview.WebViewAgreement
import com.QwertyNetworks.ai_speaker.databinding.ActivityShowWebAgreementsBinding
import java.util.*

class ShowWebAgreements: AppCompatActivity() {

    lateinit var binding: ActivityShowWebAgreementsBinding
    val lang = Locale.getDefault().language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowWebAgreementsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WebViewAgreement().showWebViewHTML(binding.webViewAgreemets, "https://qaim.me/$lang/agreement")

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}