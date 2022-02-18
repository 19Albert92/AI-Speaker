package com.QwertyNetworks.ai_speaker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.UsesCase.models.ValidateRememberEmail
import com.QwertyNetworks.ai_speaker.UsesCase.usesRemember.Validation
import com.QwertyNetworks.ai_speaker.UsesCase.webview.WebViewAgreement
import com.QwertyNetworks.ai_speaker.databinding.ActivityRememberBinding

class RememberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRememberBinding

    private lateinit var webView: WebViewAgreement

    private var validation: Validation = Validation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRememberBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        title = getText(R.string.rememberPassword_title)

        initialBottons()
    }
    private fun initialBottons() {
        binding.btnBack.setOnClickListener {
            finish()
        }
        //валидация на ноль
        validation.validateToEmpty(ValidateRememberEmail(email = binding.rememberEmail, layoutEmail = binding.inputLayoutRememberEmail), button = binding.btnLogin)
    }
}