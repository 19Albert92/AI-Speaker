package com.QwertyNetworks.ai_speaker.ui.main.fragments


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.QwertyNetworks.ai_speaker.MyClass
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.databinding.MainFragmentBinding
import com.QwertyNetworks.ai_speaker.UsesCase.webview.WebViewAgreement
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import com.QwertyNetworks.ai_speaker.ui.main.viewModel.MainViewModel
import com.QwertyNetworks.ai_speaker.ui.main.media.MediaPlayerVoice
import com.QwertyNetworks.ai_speaker.ui.main.media.MediaRecorderVoice
import com.QwertyNetworks.ai_speaker.ui.main.media.permission.PermissionRecorder
import java.io.File
import java.io.IOException
import java.sql.Timestamp
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    class MyMainViewClass{
        companion object{
            @SuppressLint("StaticFieldLeak") var activity: Activity? = null
        }
    }

    private lateinit var viewModel: MainViewModel

    private val audioSaveFile: String = (Environment.getExternalStorageDirectory().absolutePath + "/" + "recordingAudio.Ogg")

    val filevoice = File(audioSaveFile.toString())

    private val mediaRecorder = MediaRecorder()
    private val mediaRecorderVoice = MediaRecorderVoice(audioSaveFile,  mediaRecorder )

    private val mediaPlayer = MediaPlayer()
    private val mediaPlayerVoice = MediaPlayerVoice(mediaPlayer, audioSaveFile)

    private lateinit var permissionRecorder: PermissionRecorder

    private var webview:WebViewAgreement = WebViewAgreement()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding

    val lng = Locale.getDefault().language

    val md5shif = com.QwertyNetworks.ai_speaker.UsesCase.config.md5()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        initialWebView()
        initialFloatButtons()
        MyMainViewClass.activity = this.activity
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun initialWebView() {

        // unitime
        val time = Timestamp(System.currentTimeMillis()).toString()

        val pref = context?.getSharedPreferences("User_information", Context.MODE_PRIVATE)
        val state = pref?.getString(Constance.USER_ID_KEY, "")

        // кодировка md5
        val md5 = time + "XHD!!@69e" + state
        val md =  md5shif.md5(md5)

        val extraHeaders: MutableMap<String, String> = HashMap()
        extraHeaders["Authorization"] = "Bearer $time-$md"
        extraHeaders["Accept-language"] = lng

        val MY_USER_AGENT = "Mozilla/4.0 (compatible; Universion/1.0; Android; --$state--; +https://qwertynetworks.com)"

        // показ webview
        webview.showWebView(
            webView = binding!!.mainWebView,
            url = "https://mybusines.app/$lng/assistant",
            user_agent = MY_USER_AGENT,
            parameters = extraHeaders,
            context = context!!)
        // разрешение
        permissionRecorder = PermissionRecorder(context = context)

        Log.d(Constance.LOG_TAG, "path file voice: $filevoice")
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
                        ActivityCompat.requestPermissions(
                            MyClass.activity!!, arrayOf(
                                Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ), 1)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    imageRecording.visibility = View.INVISIBLE
                    readFloatBtn.setImageResource(R.drawable.ic_baseline_keyboard_voice_24)

                    //отстанавливается запись
                    mediaRecorderVoice.stopRecorder()

                    _binding!!.mainWebView.evaluateJavascript("javascript:get_voice($filevoice)", null)

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