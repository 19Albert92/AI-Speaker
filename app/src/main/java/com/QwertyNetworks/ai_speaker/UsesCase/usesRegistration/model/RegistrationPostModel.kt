package com.QwertyNetworks.ai_speaker.UsesCase.usesRegistration.model

data class RegistrationPostModel(
    val registrationUser: RegistrationUser,
    val birthday: String,
    val birthmonth: String,
    val birthyear: String
)
