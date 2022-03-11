package com.QwertyNetworks.ai_speaker.ui.main.view

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.appcompat.app.AlertDialog
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.UsesCase.usesSettingsAi.Validation
import com.QwertyNetworks.ai_speaker.UsesCase.usesSettingsAi.model.SearchNameAi
import com.QwertyNetworks.ai_speaker.databinding.ActivitySettingsAiBinding
import com.QwertyNetworks.ai_speaker.db.DBHelper
import com.QwertyNetworks.ai_speaker.db.preferences.PreferencesOther
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class SettingsActivityAi : AppCompatActivity() {

    lateinit var binding: ActivitySettingsAiBinding

    private val nameApi = "https://qaim.me/myai"

    val preferencesOther = PreferencesOther()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsAiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //инициализация кнопок
        initialButtons()

        //инициализация текста
        initialText()
    }

    fun initialButtons() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        // переход на информацию про бота
        binding.informationToBots.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(nameApi)
            startActivity(intent)
        }

        //проверка на empty
        Validation().validateToEmpty(SearchNameAi(
            binding.inputNameBots),
            binding.btnEnterAi)

        //кнопка выбора ai
        binding.btnEnterAi.setOnClickListener {
           if (binding.inputNameBots.text.toString() != "") {
                CoroutineScope(Dispatchers.IO).launch {
                val response = DBHelper().ai_valid(SearchNameAi(binding.inputNameBots))

                withContext(Dispatchers.Main) {
                    if (response == "ERROR") {
                        AlertDialog.Builder(this@SettingsActivityAi).apply {
                            setTitle("")
                            setMessage("Такого AI не существует! Или неправильно набран ID, попробуйте ввести еще раз")
                            setNegativeButton("Ok") { _, _ -> }
                            create().show()
                        }
                    } else if (response == "OK"){
                        AlertDialog.Builder(this@SettingsActivityAi).apply {
                            setTitle("")
                            setMessage("Соединение с AI полученно!")
                            setNegativeButton("Ok") { _, _ ->
                               setNameAiBots(binding.inputNameBots.text.toString())
                               println("uri ai name: ${getNameAiBots()}")
                               finish()
                            }
                            create().show()
                        }
                    }
                }
             }
           }
        }

        //кнопка сброса
        binding.btnReset.setOnClickListener {
            AlertDialog.Builder(this@SettingsActivityAi).apply {
                setTitle("")
                setMessage("По умолчанию выбрано AI assistant")
                setNegativeButton("Ok") { _, _ ->
                    setNameAiBots("AI")
                    println("uri ai name: ${getNameAiBots()}")
                    finish()
                }
                create().show()
            }
        }
    }
    fun initialText() {
        val span: Spannable = SpannableString(nameApi)
        span.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.main_color)),
            0,
            nameApi.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        span.setSpan(
            StyleSpan(Typeface.ITALIC),
            0,
            nameApi.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        span.setSpan(
            RelativeSizeSpan(1.2f),
            0,
            nameApi.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        binding.informationToBots.append(span)
    }

    fun setNameAiBots(saveNameAI: String) {
        preferencesOther.setToSharedString(
            key = Constance.AI_NAME_IS,
            text = saveNameAI,
            name = Constance.NAME_AI_CONFIG,
            context = applicationContext)
    }

    fun getNameAiBots(): String {
        val pref = applicationContext.getSharedPreferences(Constance.NAME_AI_CONFIG, Context.MODE_PRIVATE)
        return pref.getString(Constance.AI_NAME_IS, "").toString()
    }
}