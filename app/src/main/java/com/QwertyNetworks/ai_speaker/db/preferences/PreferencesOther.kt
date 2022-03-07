package com.QwertyNetworks.ai_speaker.db.preferences

import android.content.Context

class PreferencesOther() {
    fun getToSharedString(key: String,name: String, context: Context): Boolean? {
        val pref = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        return pref?.getBoolean(key, false)
    }

    fun setToSharedString(key: String, text: String, name: String, context: Context) {
        context.getSharedPreferences(name, Context.MODE_PRIVATE).apply {
            val editor = edit()
            editor.putString(key, text)
            editor.commit() // или apply()
        }
    }

    fun setToUserSystem(key: String, isSystem: Boolean, name: String, context: Context) {
        context.getSharedPreferences(name, Context.MODE_PRIVATE).apply {
            val editor = edit()
            editor.putBoolean(key, isSystem)
            editor.commit() // или apply()
        }
    }
}