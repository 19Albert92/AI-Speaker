package com.QwertyNetworks.ai_speaker.db

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import com.QwertyNetworks.ai_speaker.UsesCase.models.RegistrationUser
import com.QwertyNetworks.ai_speaker.db.preferences.ApiService
import okhttp3.*
import retrofit2.HttpException
import retrofit2.Retrofit
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder
import retrofit2.converter.scalars.ScalarsConverterFactory


class DBHelper {

    //time zona
    @SuppressLint("NewApi")
    val zone = ZoneId.systemDefault().rules.toString()
    val newres: List<String> = zone.split('=')
    val newres2 = newres[1].split(',').toString()
    var newres3 = newres2.split(':')
    var resultTimeZona = newres3[0].drop(1).drop(1).toInt()

    suspend fun register (
        RegistrationUser: RegistrationUser,
        birthday: String,
        birthmonth: String,
        birthyear: String,
        context: Context
    ) : String {

        var isRegisterTrue = ""

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://qwertynetworks.com")
//            .baseUrl("https://test.mybusines.app")//https://test.mybusines.app/api/test_post
//            .addConverterFactory(create(gson))
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())

            .client(okHttpClient)
            .build()

        val servise = retrofit.create(ApiService::class.java)

        val lng = Locale.getDefault().language

        var sdawd = GsonBuilder().setPrettyPrinting().create()

        try {
            val response = servise.setRegister(
                thisprojectid = 11,
                this_http_host = "qaim.me",
                lng = lng,
                email = RegistrationUser.email.text.toString(),
                mailretry = "",
                password = "",
                yourname = RegistrationUser.name.text.toString(),
                surname = RegistrationUser.lastName.text.toString(),
                birthdateday = birthday.toInt(),
                birthdatemonth = birthmonth.toInt(),
                birthdateyear = birthyear.toInt(),
                checkID = 1,
                userUTC = resultTimeZona,
                returnurl = ""
            )
            isRegisterTrue = response
            println(response)

        } catch (e: HttpException) {
            println("Error:  ${e.code()}")
        }
        return isRegisterTrue
    }
}