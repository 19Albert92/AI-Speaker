package com.QwertyNetworks.ai_speaker.UsesCase.textToSpeech

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import com.QwertyNetworks.ai_speaker.ui.main.fragments.MainFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.droidsonroids.gif.GifImageView
import java.util.*

class SpeechToTexts(
    var extras: TextView,
    var gif: GifImageView,
    var button: FloatingActionButton,
    var webview: WebView): RecognitionListener, Activity() {

    lateinit var speechRecognizer: SpeechRecognizer

    fun initSpeech(activity: Activity) {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity)
        Log.d(Constance.LOG_TAG,"is recognition available: ${SpeechRecognizer.isRecognitionAvailable(activity)}")
        speechRecognizer.setRecognitionListener(this)
    }

    fun speechStart() {
        speechRecognizer.startListening(forRecIntent())
    }
    override fun onReadyForSpeech(params: Bundle?) {
        Log.d(Constance.LOG_TAG, "onReadyForSpeech")
    }

    override fun onBeginningOfSpeech() {
        Log.d(Constance.LOG_TAG, "onBeginningOfSpeech")
    }

    override fun onRmsChanged(rmsdB: Float) {
        Log.d(Constance.LOG_TAG, "onRmsChanged")
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        Log.d(Constance.LOG_TAG, "onBufferReceived")
    }

    override fun onEndOfSpeech() {
        Log.d(Constance.LOG_TAG, "onEndOfSpeech")
    }

    override fun onError(error: Int) {
        val errorMessage: String = getErrorText(error)
        Log.d(Constance.LOG_TAG, "onError: $errorMessage")
    }

    fun getErrorText(error: Int): String {
        var message = when(error) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording Error"
            SpeechRecognizer.ERROR_CLIENT -> "AClient side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> "No match"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Recognizer busy"
            SpeechRecognizer.ERROR_SERVER -> "Error from server"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
            else -> "Didn`t understand, please try again"
        }
        return message
    }

    override fun onResults(results: Bundle?) {
        val matches = results!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        var text = ""
        for (result in matches!!.iterator()) {text = """
            $result""" .trimIndent()
        }

        extras.text = text
        gif.visibility = View.INVISIBLE
        button.setImageResource(R.drawable.ic_baseline_keyboard_voice_24)
    }

    override fun onPartialResults(partialResults: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        val matches = params!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        println(matches)
//        webview.loadUrl("javascript:get_voice('${matches}')")
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_UP){
            speechRecognizer.stopListening();
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                speechRecognizer.startListening(forRecIntent())
                } else {
                    Toast.makeText(applicationContext, "Permission не полученно", Toast.LENGTH_SHORT).show()
                }

    }

    fun forRecIntent(): Intent {
        var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "US-en")
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        return intent
    }
}