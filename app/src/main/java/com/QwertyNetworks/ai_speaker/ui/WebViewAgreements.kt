package com.QwertyNetworks.ai_speaker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.UsesCase.webview.WebViewAgreement
import com.QwertyNetworks.ai_speaker.databinding.ActivityWebViewAgreementsBinding

class WebViewAgreements : AppCompatActivity(),BackPressed  {

    lateinit var binding: ActivityWebViewAgreementsBinding

    var pathHTML = "file:///android_asset/agreements.html"
    var webViewAgreements = WebViewAgreement()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewAgreementsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webViewAgreements.showWebViewHTML(binding.webViewAgreemets, pathHTML)
        backPressed()
    }

    override fun backPressed() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}