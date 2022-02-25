package com.QwertyNetworks.ai_speaker.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.QwertyNetworks.ai_speaker.MainActivity
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.UsesCase.usesLogin.model.ValidateLogin
import com.QwertyNetworks.ai_speaker.UsesCase.usesLogin.Validation
import com.QwertyNetworks.ai_speaker.UsesCase.usesLogin.model.LoginUser
import com.QwertyNetworks.ai_speaker.databinding.ActivityLoginBinding
import com.QwertyNetworks.ai_speaker.db.DBHelper
import com.QwertyNetworks.ai_speaker.db.preferences.PreferencesOther
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var validate = Validation()

    private var dbHelper = DBHelper()

    private var preferencesOther = PreferencesOther()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialBinding()
    }
     private fun initialBinding() {
         //кнопка регистрации
         binding.btnRegistration.setOnClickListener {
             val intent = Intent(this, RegistrationActivity::class.java)
             startActivity(intent)
         }
         //валидация input
         validate.validOnceEdit(
             ValidateLogin(
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

         //кнопка логин
         binding.btnLogin.setOnClickListener {

             var resultLogin: String

             CoroutineScope(Dispatchers.IO).launch {
                 resultLogin = dbHelper.login(LoginUser(
                     binding.loginEmail,
                     binding.loginPassword
                 ))

                 withContext(Dispatchers.Main){
                     if(isNumber(resultLogin)) {

                         val textresult = "@qn$resultLogin:qaim.me"

                         println("this user id: $textresult")

                         preferencesOther.setToSharedString(Constance.USER_ID_KEY,textresult,"User_information",this@LoginActivity)

                         val intent = Intent(this@LoginActivity, MainActivity::class.java)
                         startActivity(intent)

                     } else {
                         AlertDialog.Builder(this@LoginActivity).apply {
                             setTitle("")
                             setMessage(resultLogin)
                             setNegativeButton("Ok") { _, _ -> }
                             create().show()
                         }
                     }
                 }
             }
         }
     }

    companion object {
        //очистка полей
        private fun emptyInputs(view: TextInputEditText) {
            view.text = null
        }

//        fun isNumber(text: String, char: String): Boolean {
//            return text.contains(char)
//        }
        fun isNumber(text: String): Boolean {
        val test = text.toIntOrNull()
        println(test)
        return test != null
        }
    }
}