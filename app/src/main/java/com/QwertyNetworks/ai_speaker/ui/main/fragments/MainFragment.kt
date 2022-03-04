package com.QwertyNetworks.ai_speaker.ui.main.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.QwertyNetworks.ai_speaker.MyClass
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.databinding.MainFragmentBinding
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import com.QwertyNetworks.ai_speaker.ui.main.viewModel.MainViewModel
import com.QwertyNetworks.ai_speaker.ui.main.media.permission.PermissionRecorder
import java.sql.Timestamp
import java.util.*
import android.net.Uri
import android.os.Build
import android.webkit.*
import com.QwertyNetworks.ai_speaker.UsesCase.webview.WebAppInterface
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import com.QwertyNetworks.ai_speaker.UsesCase.textToSpeech.SpeechToTexts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

open class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    class MyMainViewClass{
        companion object{
            @SuppressLint("StaticFieldLeak") var activity: Activity? = null
        }
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var permissionRecorder: PermissionRecorder

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding

    val lng = Locale.getDefault().language

    val md5shif = com.QwertyNetworks.ai_speaker.UsesCase.config.md5()

    private var mUploadMessageArray: ValueCallback<Array<Uri>>? = null

    private val FILECHOOSER_RESULTCODE = 999

    private var uriArray: Array<Uri> = arrayOf()

    //speech
    private lateinit var tts: TextToSpeech

    //speech to text
    private lateinit var speech: SpeechToTexts

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        initialWebView()
        initialFloatButtons()
        MyMainViewClass.activity = this.activity
        speech = SpeechToTexts(
            _binding!!.texdRes,
            _binding!!.imageRecording,
            _binding!!.readFloatBtn,
            _binding!!.mainWebView)
        speech.initSpeech(activity!!)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun initialWebView() {
        // unitime
        val time = Timestamp(System.currentTimeMillis()).toString()
        Log.d(Constance.LOG_TAG, time)

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
        showWebView(
            webView = binding!!.mainWebView,
            url = "https://qaim.me/$lng/assistant",
            user_agent = MY_USER_AGENT,
            parameters = extraHeaders,
            context = context!!)

        // разрешение
        permissionRecorder = PermissionRecorder(context = context)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initialFloatButtons() = with(binding!!) {

        readFloatBtn.setOnTouchListener { v, event ->

            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    imageRecording.visibility = View.VISIBLE
                    readFloatBtn.setImageResource(R.drawable.ic_baseline_voicemail)
                    if(permissionRecorder.checkPermissions()) {

                        //начинается запись
                        Toast.makeText(context, "action down float button", Toast.LENGTH_SHORT).show()
                    } else {
                        //проыерка на разрешения
                        ActivityCompat.requestPermissions(
                            MyClass.activity!!, arrayOf(
                                Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ), 1)
                        speech.speechStart()
                    }
                }

                //отстанавливается запись
                MotionEvent.ACTION_UP -> {
                    //функция для вывода и отправки на через webview текста
                    getToTextResult()
                }
            }
            return@setOnTouchListener true
        }

        // кнопка назад(обратно на фрагмент)
        activity!!.onBackPressedDispatcher.addCallback(this@MainFragment, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance(), "fragment_1")
                    .commitNow()
            }
        })
    }

    // webview инициализация
    @SuppressLint("SetJavaScriptEnabled")
    fun showWebView(webView: WebView, url: String, user_agent: String, parameters: MutableMap<String, String>, context: Context) {
        webView.apply {
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.javaScriptEnabled = true
            settings.allowContentAccess = true
            settings.allowFileAccess = true
            settings.userAgentString = user_agent

            // интерфейс для моста между webview и javascript
            addJavascriptInterface(
                WebAppInterface(
                mContext = context,
                webView = this),"Android")

//             Enable zooming in web view
            settings.displayZoomControls = false
            settings.loadsImagesAutomatically = true
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

            settings.loadsImagesAutomatically = true
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            settings.domStorageEnabled = true
            settings.allowContentAccess = true
            settings.allowFileAccess = true
//             Enable disable images in web view
            settings.blockNetworkImage = false
//             Whether the WebView should load image resources
            settings.loadsImagesAutomatically = true

            // web client инициализация для webview
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String) : Boolean {
                    view?.loadUrl(url);
                    return true
                }
                @TargetApi(Build.VERSION_CODES.N)
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    view?.loadUrl(request?.url.toString())
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    _binding!!.readFloatBtn.visibility = View.VISIBLE
                    _binding!!.loadedLayout.visibility = View.INVISIBLE
                }
            }
            webChromeClient = getWebClientChrome()
            loadUrl(url,parameters)
        }
    }

    // web client chrome инициализация для webview (дополнительно)
    fun getWebClientChrome(): WebChromeClient {
        return object : WebChromeClient(){
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                println("this is show onShowFileChooser")
                mUploadMessageArray?.onReceiveValue(null)
                if (filePathCallback != null) {
                    mUploadMessageArray = filePathCallback
                }

                val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                contentSelectionIntent.type = "*/*"
                val intentArray: Array<Intent?> = arrayOfNulls(0)

                val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "File Chooser")
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)

                MyClass.activity!!.startActivityForResult(contentSelectionIntent, FILECHOOSER_RESULTCODE)
                return true
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                _binding!!.loadedProgress.progress = newProgress
                _binding!!.loadedNumbers.hint = "$newProgress%"
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_CANCELED) {
            mUploadMessageArray?.onReceiveValue(null);
        } else if (resultCode == Activity.RESULT_OK) {
            mUploadMessageArray?.onReceiveValue(uriArray);
        }
    }

    override fun onResume() {
        super.onResume()
        if (mUploadMessageArray == null)
            return;
        mUploadMessageArray?.onReceiveValue(uriArray);
        mUploadMessageArray = null;
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    fun getToTextResult() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Thread.sleep(4000)
            } catch (ex: Exception) {
                ex.localizedMessage
            }
            withContext(Dispatchers.Main) {
                Log.d(Constance.LOG_TAG, binding!!.texdRes.text.toString())
                var resultText = binding!!.texdRes.text.toString()
                if (resultText != "") {
                    _binding!!.mainWebView.loadUrl("javascript:get_voice('${resultText}')")
                    "".also { resultText = it }
                }
            }
        }
    }
}