package com.QwertyNetworks.ai_speaker.UsesCase.webview

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.QwertyNetworks.ai_speaker.db.preferences.PreferencesOther
import com.QwertyNetworks.ai_speaker.ui.LoginActivity
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import com.google.gson.JsonObject
import org.jetbrains.annotations.NotNull
import org.json.JSONObject
import java.lang.reflect.Array
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.HashMap

class WebAppInterface(private val mContext: Context, private val webView: WebView) {

    private var preferencesOther = PreferencesOther()
    lateinit var tts: TextToSpeech
    lateinit var voice: Voice

    @JavascriptInterface
    fun logout(text: String) {
        val intent = Intent(mContext, LoginActivity::class.java)
        mContext.startActivity(intent)

        preferencesOther.setToSharedString(Constance.USER_ID_KEY,"","User_information",mContext)
        webView.reload()
    }

    @JavascriptInterface
    fun readToText(text: String, lang: String) {
        val executor = Executors.newFixedThreadPool(2)
        for (i in 0..2) {
            val worker = Runnable {
                println("Hello this is thread: $text")
            }
            executor.execute(worker)
        }
        executor.shutdown()
        while (!executor.isTerminated) {
            readText(text, lang)
            println("text: $text, lang: $lang")
        }
    }

    fun readText(text: String, lang: String) {
        tts = TextToSpeech(mContext, TextToSpeech.OnInitListener {
            if(it != TextToSpeech.ERROR) {
                val a: Set<String> = HashSet()
                if(Locale.getDefault().language == "ru") {
                    voice = Voice("ru-ru-x-ruf-local", Locale.getDefault(), 400, 200, false, a)
                } else if (Locale.getDefault().language == "en") {
                    voice = Voice("en-us-x-sfg#female_2-local", Locale.getDefault(), 400, 200, true, a)
                }
                tts.language = Locale(lang)
                tts.voice = voice
                val speech = 1.26f
                tts.setPitch(speech)

                val speed = 1.15f
                tts.setSpeechRate(speed)

                tts.speak(text, TextToSpeech.QUEUE_ADD, null, "")
            }
        })
    }
}