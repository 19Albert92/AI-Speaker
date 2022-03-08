package com.QwertyNetworks.ai_speaker.ui.main.view

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.UsesCase.usesRemember.model.RememberPassword
import com.QwertyNetworks.ai_speaker.UsesCase.usesRemember.model.ValidateRememberEmail
import com.QwertyNetworks.ai_speaker.UsesCase.usesRemember.Validation
import com.QwertyNetworks.ai_speaker.UsesCase.webview.WebViewAgreement
import com.QwertyNetworks.ai_speaker.databinding.ActivityRememberBinding
import com.QwertyNetworks.ai_speaker.db.DBHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RememberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRememberBinding

    private lateinit var webView: WebViewAgreement

    private var validation: Validation = Validation()

    private var dbHelper = DBHelper()

    private var returnString: String = ""

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
        validation.validateToEmpty(
            ValidateRememberEmail(
            email = binding.rememberEmail,
            layoutEmail = binding.inputLayoutRememberEmail),
            button = binding.btnLogin)

        binding.btnLogin.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                returnString = dbHelper.rememberPassword(RememberPassword(binding.rememberEmail))

                val builder = AlertDialog.Builder(this@RememberActivity)

                builder.setTitle("")

                builder.setMessage(returnString)

                builder.setNegativeButton("Ok") { _, _ -> }

                withContext(Dispatchers.Main) {
                    if(returnString != "OK") {
                        var dialog: AlertDialog = builder.create()
                        dialog.show()
                    } else {
                        AlertDialog.Builder(this@RememberActivity).apply {
                            setTitle("")
                            setMessage(R.string.successfulyRegistration)
                            setNeutralButton("Ok") { _, _ ->
                                val intent = Intent(this@RememberActivity, LoginActivity::class.java)
                                startActivity(intent)
                            }
                            create().show()
                        }
                    }

                }
            }
        }
    }
}