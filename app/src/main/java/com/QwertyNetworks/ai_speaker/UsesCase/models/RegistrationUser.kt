package com.QwertyNetworks.ai_speaker.UsesCase.models

import com.google.android.material.textfield.TextInputEditText

data class RegistrationUser(
    var email: TextInputEditText,
    var name: TextInputEditText,
    var lastName: TextInputEditText,
    var dateBirth: String
)
