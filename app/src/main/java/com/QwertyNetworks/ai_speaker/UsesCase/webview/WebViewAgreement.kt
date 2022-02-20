package com.QwertyNetworks.ai_speaker.UsesCase.webview

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.QwertyNetworks.ai_speaker.MyClass.Companion.activity


class WebViewAgreement() {

    @SuppressLint("JavascriptInterface")
    fun showWebView(webView: WebView, url: String, user_agent: String, parameters: MutableMap<String, String>, context: Context) {

        webView.apply {
            settings.javaScriptEnabled = true
            settings.allowContentAccess = true
            settings.allowFileAccess = true
            settings.userAgentString = user_agent
            settings.javaScriptCanOpenWindowsAutomatically = true
            addJavascriptInterface(WebAppInterface(
                mContext = context,
                webView = this),"Android")

            // Enable zooming in web view
            settings.setSupportZoom(false)
            settings.builtInZoomControls = false
            settings.displayZoomControls = false

            // Whether the WebView should load image resources
            settings.loadsImagesAutomatically = true

            // WebView settings
            webView.fitsSystemWindows = true

            // Zoom web view text
            settings.textZoom = 100

            // Enable disable images in web view
            settings.blockNetworkImage = false
            // Whether the WebView should load image resources
            settings.loadsImagesAutomatically = true

            loadUrl(url,parameters)

            val newWebViewClient: WebViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(view: WebView?, url: String) : Boolean {
                    // все ссылки, в которых содержится домен
                    // будут открываться внутри приложения
//                    if (url.contains("my-site.ru")) {
//                        return false
//                    }
//                    // все остальные ссылки будут спрашивать какой браузер открывать
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    activity?.startActivity(intent)
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
            }
            webViewClient = newWebViewClient
        }
    }

    fun showWebViewHTML(webView: WebView, url: String) {

        webView.apply {
//            settings.javaScriptEnabled = true
            settings.allowContentAccess = true
            settings.allowFileAccess = true
            settings.userAgentString = Constance.USER_AGENT
            settings.javaScriptCanOpenWindowsAutomatically = true

            loadUrl(url)

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

}