package com.QwertyNetworks.ai_speaker.db.preferences

import android.content.Context

class PreferencesOther {
    fun getToSharedString(key: String, text: String, name: String, context: Context){
        context.getSharedPreferences(name, Context.MODE_PRIVATE).apply {
            getString(key, text)
        }
    }

    fun editToSharedString(key: String, text: String, name: String, context: Context) {
        context.getSharedPreferences(name, Context.MODE_PRIVATE).apply {
            val editor = edit()
            editor.putString(key, text)
            editor.apply() // или commit()
        }
    }
}