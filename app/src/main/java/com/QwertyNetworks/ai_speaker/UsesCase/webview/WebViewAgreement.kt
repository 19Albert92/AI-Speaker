package com.QwertyNetworks.ai_speaker.UsesCase.webview

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.*
import com.QwertyNetworks.ai_speaker.ui.main.view.MyClass.Companion.activity
import android.webkit.WebChromeClient
import android.webkit.ValueCallback

import android.webkit.WebView

class WebViewAgreement() : Activity() {

    private var mUploadMessageArray: ValueCallback<Array<Uri>>? = null

    private var FILECHOOSER_RESULTCODE = 999

    private var uriArray: Array<Uri> = arrayOf()

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    fun showWebView(webView: WebView, url: String, user_agent: String, parameters: MutableMap<String, String>, context: Context) {
        webView.apply {
           settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.allowContentAccess = true
            settings.allowFileAccess = true
//            settings.userAgentString = user_agent

//            addJavascriptInterface(WebAppInterface(
//                mContext = context,
//                webView = this),"Android")

//             Enable zooming in web view
            settings.builtInZoomControls = true
//
            settings.displayZoomControls = false
//
////             Whether the WebView should load image resources
            settings.loadsImagesAutomatically = true

//             Zoom web view text
//            settings.textZoom = 100
//
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
//
            settings.loadsImagesAutomatically = true
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            settings.domStorageEnabled = true
            settings.allowContentAccess = true
            settings.allowFileAccess = true

//             Enable disable images in web view
            settings.blockNetworkImage = false
////             Whether the WebView should load image resources
            settings.loadsImagesAutomatically = true


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
            }
            webChromeClient = getWebClientChrome()
            loadUrl(url,parameters)
        }
    }
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
                activity!!.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE)
                return true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_CANCELED) {

            mUploadMessageArray?.onReceiveValue(null);
        } else if (resultCode == RESULT_OK) {

            mUploadMessageArray?.onReceiveValue(uriArray);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun showWebViewHTML(webView: WebView, loadWeb: String) {

        webView.apply {
            settings.javaScriptEnabled = true
            settings.allowContentAccess = true
            settings.allowFileAccess = true
            settings.userAgentString = Constance.USER_AGENT
            settings.javaScriptCanOpenWindowsAutomatically = true

            loadUrl(loadWeb)

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