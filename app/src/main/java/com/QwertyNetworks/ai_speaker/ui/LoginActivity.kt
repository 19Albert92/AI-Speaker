package com.QwertyNetworks.ai_speaker.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.UsesCase.models.ValidateLogin
import com.QwertyNetworks.ai_speaker.UsesCase.usesLogin.Validation
import com.QwertyNetworks.ai_speaker.databinding.ActivityLoginBinding
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var validate = Validation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        title = getText(R.string.login_title)

        initialBinding()
    }
     private fun initialBinding() {
         //кнопка регистрации
         binding.btnRegistration.setOnClickListener {
             val intent = Intent(this, RegistrationActivity::class.java)
             startActivity(intent)
         }
         //валидация input
         validate.validOnceEdit(ValidateLogin(
             emailEdText = binding.loginEmail,
             passwordEdText = binding.loginPassword,
             layoutEmail = binding.layoutLoginEmail,
             layoutlogin = binding.layoutLoginPassword,
             textError = resources.getString(R.string.text_error)),
             buttonAction = binding.btnLogin,
             )

         //кнопка вспомнить пароль
         binding.btnRememberPassword.setOnClickListener {
             val intent = Intent(this, RememberActivity::class.java)
             startActivity(intent)
         }
     }

    companion object {
        //очистка полей
        private fun emptyInputs(view: TextInputEditText) {
            view.text = null
        }
    }
}