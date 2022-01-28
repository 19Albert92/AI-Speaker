package com.QwertyNetworks.ai_speaker.ui.main


import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.QwertyNetworks.ai_speaker.databinding.MainFragmentBinding
import com.QwertyNetworks.ai_speaker.ui.Constance

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

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
                    Toast.makeText(context, "action down float button", Toast.LENGTH_SHORT).show()
                }
                MotionEvent.ACTION_UP -> {
                    Toast.makeText(context, "action up float button", Toast.LENGTH_SHORT).show()
                }
            }

            return@setOnTouchListener true
        }

        playFloatBtn.setOnClickListener {
            Toast.makeText(context, "action play voice", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}