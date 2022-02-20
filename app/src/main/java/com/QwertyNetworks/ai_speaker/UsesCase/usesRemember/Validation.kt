package com.QwertyNetworks.ai_speaker.UsesCase.usesRemember

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.QwertyNetworks.ai_speaker.UsesCase.usesRemember.model.ValidateRememberEmail

class Validation {
    fun validateToEmpty(
        rememberEmail: ValidateRememberEmail,
        button: Button) {
        rememberEmail.email.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s?.length == 0) {
                    button.alpha = .5F
                    button.isClickable = false
                    rememberEmail.layoutEmail.error = "Поле обязательно к заполнению"
                } else {
                    button.alpha = 1F
                    button.isClickable = true
                    rememberEmail.layoutEmail.error = null
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s?.length == 0) {
                    button.alpha = .5F
                    button.isClickable = false
                    rememberEmail.layoutEmail.error = "Поле обязательно к заполнению"
                } else {
                    button.alpha = 1F
                    button.isClickable = true
                    rememberEmail.layoutEmail.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0) {
                    button.alpha = .5F
                    button.isClickable = false
                    rememberEmail.layoutEmail.error = "Поле обязательно к заполнению"
                } else {
                    button.alpha = 1F
                    button.isClickable = true
                }
            }
        })
    }
}