package com.QwertyNetworks.ai_speaker.db

import android.content.Context
import android.content.SharedPreferences
import com.QwertyNetworks.ai_speaker.ui.constance.Constance

class PreferenceHelper(private val context: Context) {

    private var app_prefs: SharedPreferences = context.getSharedPreferences(
        Constance.SHAREDPREF,
        Context.MODE_PRIVATE
    )
    /* вошел ли пользователь в систему */
    fun putIsLogin(loginorout: Boolean) {
        val edit = app_prefs.edit()
        edit.putBoolean(Constance.INTRO, loginorout)
        edit.commit()
    }

    fun getIsLogin(): Boolean {
        return app_prefs.getBoolean(Constance.INTRO, false)
    }

    /* все что связанно с именем */
    fun putName(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(Constance.NAME, loginorout)
        edit.commit()
    }

    fun getNames(): String? {
        return app_prefs.getString(Constance.NAME, "")
    }

    /* все что связанно с email */
    fun putEmail(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(Constance.EMAIL, loginorout)
        edit.commit()
    }

    fun getEmail(): String? {
        return app_prefs.getString(Constance.EMAIL, "")
    }

    /* все что связанно с lastname */
    fun putLastName(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(Constance.LASTNAME, loginorout)
        edit.commit()
    }

    fun getLastName(): String? {
        return app_prefs.getString(Constance.LASTNAME, "")
    }
}