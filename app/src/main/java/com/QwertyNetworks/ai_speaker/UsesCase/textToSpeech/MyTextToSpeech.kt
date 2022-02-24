package com.QwertyNetworks.ai_speaker.UsesCase.textToSpeech

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import java.util.*
import kotlin.collections.HashSet

class MyTextToSpeech(var text: String, var tts: TextToSpeech, val context: Context): TextToSpeech.OnInitListener {

    override fun onInit(status: Int) {
        tts = TextToSpeech(context, this)
        val a: Set<String> = HashSet()
        val voice = Voice(
            "ru-ru-x-ruf-local",
            Locale.getDefault(),
            400,
            200,
            false,
            a
        )

        tts.voice = voice

        val speech = 1.26f
        tts.setPitch(speech)

        val speed = 1.15f
        tts.setSpeechRate(speed)

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")

    }

    fun startVoice() {

    }
}