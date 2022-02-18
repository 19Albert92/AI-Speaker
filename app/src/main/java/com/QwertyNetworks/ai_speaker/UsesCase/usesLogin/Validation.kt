package com.QwertyNetworks.ai_speaker.UsesCase.usesLogin

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.QwertyNetworks.ai_speaker.UsesCase.models.ValidateLogin

class Validation() {
    fun validOnceEdit(
        validate: ValidateLogin,
        buttonAction: Button){

        var bool1 = false
        var bool2 = false

        validate.emailEdText.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { bool1 = s?.length != 0 }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { bool1 = s?.length != 0 }

            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0 ) {
                    buttonAction.alpha = .5F
                    validate.layoutEmail.error = validate.textError
                    bool1 = false
                } else {
                    bool1 = true
                    if(bool2 && bool1) {
                        buttonAction.alpha = 1F
                        validate.layoutEmail.error = null
                    }
                }
            }
        })

        validate.passwordEdText.addTextChangedListener (object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { bool2 = s?.length != 0 }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { bool2 = s?.length != 0 }

            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0 ) {
                    buttonAction.alpha = .5F
                    validate.layoutlogin.error = validate.textError
                    bool2 = false
                } else {
                    bool2 = true
                    if(bool2 && bool1) {
                        buttonAction.alpha = 1F
                        validate.layoutlogin.error = null
                    }
                }
            }
        })
    }

}