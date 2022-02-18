package com.QwertyNetworks.ai_speaker.UsesCase.models

data class RegistrationPostModel(
    val registrationUser: RegistrationUser,
    val birthday: String,
    val birthmonth: String,
    val birthyear: String
)
