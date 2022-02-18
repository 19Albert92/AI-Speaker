package com.QwertyNetworks.ai_speaker.ui.main.fragments


import android.Manifest
import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.QwertyNetworks.ai_speaker.MyClass
import com.QwertyNetworks.ai_speaker.databinding.MainFragmentBinding
import com.QwertyNetworks.ai_speaker.UsesCase.webview.WebViewAgreement
import com.QwertyNetworks.ai_speaker.ui.main.viewModel.MainViewModel
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

    private var webview:WebViewAgreement = WebViewAgreement()

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
        //показ webview
        webview.showWebView(binding!!.mainWebView)

        permissionRecorder = PermissionRecorder(context = context)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initialFloatButtons() = with(binding!!) {

        readFloatBtn.setOnTouchListener { v, event ->

            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if(permissionRecorder.checkPermissions()) {
                        mediaRecorderVoice.mediaRecorder()
                        try {
                            mediaRecorderVoice.startRecorder()
                        }catch (e: IOException) {
                            e.stackTrace
                        }
                        Toast.makeText(context, "action down float button", Toast.LENGTH_SHORT).show()
                    } else {
                        ActivityCompat.requestPermissions(
                            MyClass.activity!!, arrayOf(
                                Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ), 1)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    try {
                        mediaRecorderVoice.stopRecorder()
                    } catch (e: IOException) {
                        e.stackTrace
                    }

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