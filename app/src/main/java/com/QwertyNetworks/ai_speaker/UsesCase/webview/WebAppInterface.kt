package com.QwertyNetworks.ai_speaker.UsesCase.webview

import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import com.QwertyNetworks.ai_speaker.MainActivity
import com.QwertyNetworks.ai_speaker.db.preferences.PreferencesOther
import com.QwertyNetworks.ai_speaker.ui.LoginActivity
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import com.QwertyNetworks.ai_speaker.ui.main.fragments.MainFragment

class WebAppInterface(private val mContext: Context, private val webView: WebView) {
    private var preferencesOther = PreferencesOther()

    @JavascriptInterface
    fun logout(text: String) {

        val intent = Intent(mContext, LoginActivity::class.java)
        mContext.startActivity(intent)

        preferencesOther.setToSharedString(Constance.USER_ID_KEY,"","User_information",mContext)
        webView.reload()
    }
}