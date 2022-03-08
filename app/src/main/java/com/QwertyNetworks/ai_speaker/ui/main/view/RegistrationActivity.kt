package com.QwertyNetworks.ai_speaker.ui.main.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.UsesCase.usesRegistration.model.RegistrationUser
import com.QwertyNetworks.ai_speaker.UsesCase.usesRegistration.model.ValidateRegistration
import com.QwertyNetworks.ai_speaker.UsesCase.usesRegistration.Validation
import com.QwertyNetworks.ai_speaker.databinding.ActivityRegistrationBinding
import com.QwertyNetworks.ai_speaker.db.DBHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding

    private var validate = Validation()

    private var dbHelper = DBHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialBottons()
    }

    private fun initialBottons() {
        //выбор даты рождения
        createDateBirth()

        // фокус на первое поле вода при входе
        binding.loginRegistrationEmail.requestFocus()

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

                var resultRegistration: String

                CoroutineScope(Dispatchers.IO).launch {
                    binding.apply {
                        resultRegistration = dbHelper.register( RegistrationUser(
                            email = loginRegistrationEmail,
                            name = loginRegistrationName,
                            lastName = loginRegistrationLastName,
                            dateBirth = dateText.text.toString()
                        ),  birthday = resultDate[0].trim(),
                            birthmonth = resultDate[1],
                            birthyear = resultDate[2]
                        )
                    }

                    withContext(Dispatchers.Main) {
                        if(resultRegistration != "OK") {
                            showModalWindow(resultRegistration, this@RegistrationActivity)
                        } else {
                            AlertDialog.Builder(this@RegistrationActivity).apply {
                                setTitle("")
                                setMessage(R.string.successfulyRegistration)
                                setNeutralButton("Ok") { _, _ ->
                                    val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                create().show()
                            }
                        }
                    }
                }
            }
        }

        binding.btnLoginToRegistration.setOnClickListener {
            finish()
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

    fun showModalWindow(text: String, context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle("")
            setMessage(text)
            setNegativeButton("Ok") { _, _ -> }
            create().show()
        }
    }
}