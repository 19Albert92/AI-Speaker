package com.QwertyNetworks.ai_speaker.UsesCase.usesLogin.model

import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

data class ValidateLogin(
    var emailEdText: TextInputEditText,
    var passwordEdText: TextInputEditText,
    var layoutEmail: TextInputLayout,
    var layoutlogin: TextInputLayout,
    var textError: String
)
