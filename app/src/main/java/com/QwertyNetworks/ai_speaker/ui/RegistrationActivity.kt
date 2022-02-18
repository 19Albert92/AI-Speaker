package com.QwertyNetworks.ai_speaker.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.UsesCase.models.RegistrationUser
import com.QwertyNetworks.ai_speaker.UsesCase.models.ValidateRegistration
import com.QwertyNetworks.ai_speaker.UsesCase.usesRegistration.Validation
import com.QwertyNetworks.ai_speaker.databinding.ActivityRegistrationBinding
import com.QwertyNetworks.ai_speaker.db.DBHelper
import com.QwertyNetworks.ai_speaker.db.PreferenceHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class RegistrationActivity : AppCompatActivity(), BackPressed {

    lateinit var binding: ActivityRegistrationBinding

    private var validate = Validation()

    private var dbHelper = DBHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialBottons()
        backPressed()
    }

    private fun initialBottons() {
        //выбор даты рождения
        createDateBirth()

        //валидация на пустоту
        binding.apply {
                validate.validateToEmpty(
                ValidateRegistration(
                    email = loginRegistrationEmail,
                    name = loginRegistrationName,
                    lastname = loginRegistrationLastName,
                    emailLayout = inputLayoutToRegisterEmail,
                    nameLayout = inputLayoutToRegisterName,
                    lastnameLayout = inputLayoutToRegisterLastname,
                    textError = resources.getString(R.string.text_error)
                ), button = btnRegisterToRegister,
                    textDate = dateText,
                    resultValid = resultValid
            )
        }

        //кнопка для перехода при успешной валидации

        binding.btnRegisterToRegister.setOnClickListener {
            if(binding.resultValid.text == "true") {

                var resultDate = binding.dateText.text.split('/')

                var resultBool: String

                CoroutineScope(Dispatchers.IO).launch {
                    binding.apply {
                        resultBool = dbHelper.register( RegistrationUser(
                            email = loginRegistrationEmail,
                            name = loginRegistrationName,
                            lastName = loginRegistrationLastName,
                            dateBirth = dateText.text.toString()
                        ),  birthday = resultDate[0].trim(),
                            birthmonth = resultDate[1],
                            birthyear = resultDate[2],
                            context = applicationContext
                        )
                    }

                    val builder = AlertDialog.Builder(this@RegistrationActivity)

                    builder.setTitle("")

                    builder.setMessage(resultBool)

                    builder.setPositiveButton("Ok") { _, _ -> }

                    withContext(Dispatchers.Main) {
                        if(resultBool != "OK") {
                            var dialog: AlertDialog = builder.create()
                            dialog.show()
                        } else {
                            AlertDialog.Builder(this@RegistrationActivity).apply {
                                setTitle("")
                                setMessage(R.string.successfulyRegistration)
                                setPositiveButton("Ok") { _, _ -> }
                                create().show()
                            }
                        }
//                        println("return text : $resultBool")
                    }
                }

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("ResourceAsColor")

    fun createDateBirth() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.birthDate.setOnClickListener {
            val dpd = DatePickerDialog(this,
                AlertDialog.THEME_HOLO_DARK, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                    val resultDate = " $mDay/${mMonth+1}/$mYear"
                    binding.dateText.text = resultDate
                }, year, month, day)
            dpd.show()
            dpd.datePicker.maxDate
        }
    }

    //назад
    override fun backPressed() {
        binding.btnLoginToRegistration.setOnClickListener {
            finish()
        }
    }


}