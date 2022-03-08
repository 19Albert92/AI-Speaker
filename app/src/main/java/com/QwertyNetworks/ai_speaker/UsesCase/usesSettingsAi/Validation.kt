package com.QwertyNetworks.ai_speaker.UsesCase.usesSettingsAi

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.QwertyNetworks.ai_speaker.UsesCase.usesRemember.model.ValidateRememberEmail
import com.QwertyNetworks.ai_speaker.UsesCase.usesSettingsAi.model.SearchNameAi

class Validation {
    fun validateToEmpty(
        rememberEmail: SearchNameAi,
        button: Button
    ) {
        rememberEmail.aiName.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s?.length == 0) {
                    button.alpha = .5F
                    button.isClickable = false
                } else {
                    button.alpha = 1F
                    button.isClickable = true
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s?.length == 0) {
                    button.alpha = .5F
                    button.isClickable = false
                } else {
                    button.alpha = 1F
                    button.isClickable = true
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0) {
                    button.alpha = .5F
                    button.isClickable = false
                } else {
                    button.alpha = 1F
                    button.isClickable = true
                }
            }
        })
    }
}