package com.QwertyNetworks.ai_speaker.ui.main


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.QwertyNetworks.ai_speaker.MyClass
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.databinding.MainFragmentBinding
import com.QwertyNetworks.ai_speaker.ui.Constance
import com.QwertyNetworks.ai_speaker.ui.main.media.MediaPlayerVoice
import com.QwertyNetworks.ai_speaker.ui.main.media.MediaRecorderVoice
import com.QwertyNetworks.ai_speaker.ui.main.media.permission.PermissionRecorder
import java.io.IOException

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private val audioSaveFile: String = (Environment.getExternalStorageDirectory().absolutePath + "/" + "recordingAudio.Ogg")

    private val mediaRecorder = MediaRecorder()
    private val mediaRecorderVoice = MediaRecorderVoice(audioSaveFile,  mediaRecorder )

    private val mediaPlayer = MediaPlayer()
    private val mediaPlayerVoice = MediaPlayerVoice(mediaPlayer, audioSaveFile)

    private lateinit var permissionRecorder: PermissionRecorder

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        initialWebView()
        initialFloatButtons()
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun initialWebView() {

        permissionRecorder = PermissionRecorder(context = context)

        binding!!.mainWebView.apply {
            settings.javaScriptEnabled = true
            settings.allowContentAccess = true
            settings.allowFileAccess = true
            settings.userAgentString = Constance.USER_AGENT
            settings.javaScriptCanOpenWindowsAutomatically = true

            loadUrl(Constance.LOAD_URL)

            val newWebViewClient: WebViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(view: WebView?, url: String) : Boolean {
                    view?.loadUrl(Constance.LOAD_URL)
                    return true
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    view?.loadUrl(request?.url.toString())
                    return true
                }

            }
            webViewClient = newWebViewClient
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initialFloatButtons() = with(binding!!) {

        readFloatBtn.setOnTouchListener { v, event ->

            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    imageRecording.visibility = View.VISIBLE
                    readFloatBtn.setImageResource(R.drawable.ic_baseline_voicemail)
                    if(permissionRecorder.checkPermissions()) {
                        mediaRecorderVoice.mediaRecorder()

                        //начинается запись
                        mediaRecorderVoice.startRecorder()

                        Toast.makeText(context, "action down float button", Toast.LENGTH_SHORT).show()
                    } else {
                        ActivityCompat.requestPermissions(MyClass.activity!!, arrayOf(
                            Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ), 1)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    imageRecording.visibility = View.INVISIBLE
                    readFloatBtn.setImageResource(R.drawable.ic_baseline_keyboard_voice_24)

                    //отстанавливается запись
                    mediaRecorderVoice.stopRecorder()


                    Toast.makeText(context, "action up float button", Toast.LENGTH_SHORT).show()
                }
            }

            return@setOnTouchListener true
        }

        playFloatBtn.setOnClickListener {
            mediaPlayerVoice.playMediaPlayer()
            Toast.makeText(context, "action play voice", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}