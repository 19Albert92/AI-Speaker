package com.QwertyNetworks.ai_speaker.UsesCase.models

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

data class ValidateRegistration(
    var email: TextInputEditText,
    var name: TextInputEditText,
    var lastname: TextInputEditText,
    var emailLayout: TextInputLayout,
    var nameLayout: TextInputLayout,
    var lastnameLayout: TextInputLayout,
    var textError: String
)
