package com.QwertyNetworks.ai_speaker.UsesCase.usesRegistration

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.QwertyNetworks.ai_speaker.UsesCase.usesRegistration.model.ValidateRegistration

class Validation {

    fun validateToEmpty(validate: ValidateRegistration, button: Button, textDate: TextView, resultValid: TextView){
        var email  = false
        var name = false
        var lastname = false
        var textNotNull = false
        resultValid.text = "false"



        textDate.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { textNotNull = s?.length != 0 }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { textNotNull = s?.length != 0 }
            override fun afterTextChanged(s: Editable?) {
                textNotNull = s?.length != 0
                if(email && name && lastname && textNotNull) {
                    resultValid.text = "true"
                    button.alpha = 1F
                    button.isClickable = true
                } else {
                    button.isClickable = false
                }
            }
        })

        validate.email.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { email = s?.length != 0 }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { email = s?.length != 0 }
            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0) {
                    email = false
                    button.alpha = .5F
                    button.isClickable = false
                    validate.emailLayout.error = validate.textError
                } else {
                    email = true
                    validate.emailLayout.error = null
                    if(email && name && lastname && textNotNull) {
                        resultValid.text = "true"
                        button.alpha = 1F
                        button.isClickable = true
                    }
                }
            }
        })

        validate.name.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { name = s?.length != 0 }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { name = s?.length != 0 }
            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0) {
                    name = false
                    button.isClickable = false
                    button.alpha = .5F
                    validate.nameLayout.error = validate.textError
                } else {
                    name = true
                    validate.nameLayout.error = null
                    if(email && name && lastname && textNotNull) {
                        resultValid.text = "true"
                        button.alpha = 1F
                        button.isClickable = true
                    }
                }
            }
        })

        validate.lastname.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {lastname = s?.length != 0}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {lastname = s?.length != 0}
            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0) {
                    lastname = false
                    button.alpha = .5F
                    button.isClickable = false
                    validate.lastnameLayout.error = validate.textError
                } else {
                    lastname = true
                    validate.lastnameLayout.error = null
                    if(email && name && lastname && textNotNull) {
                        resultValid.text = "true"
                        button.alpha = 1F
                        button.isClickable = true
                    }
                }
            }
        })
    }
}
