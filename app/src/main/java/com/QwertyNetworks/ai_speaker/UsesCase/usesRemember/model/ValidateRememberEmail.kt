package com.QwertyNetworks.ai_speaker.UsesCase.usesRemember.model

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

data class ValidateRememberEmail(
    val email: TextInputEditText,
    val layoutEmail: TextInputLayout
)
