package com.QwertyNetworks.ai_speaker.db

import android.annotation.SuppressLint
import android.content.Context
import com.QwertyNetworks.ai_speaker.UsesCase.usesLogin.model.LoginUser
import com.QwertyNetworks.ai_speaker.UsesCase.usesRegistration.model.RegistrationUser
import com.QwertyNetworks.ai_speaker.UsesCase.usesRemember.model.RememberPassword
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
//    val zone = ZoneId.systemDefault().rules.toString()
//    val newres: List<String> = zone.split('=')
//    val newres2 = newres[1].split(',').toString()
//    var newres3 = newres2.split(':')
//    var resultTimeZona = newres3[0].drop(1).drop(1).toInt()
    val lng = Locale.getDefault().language
    private var md5 = com.QwertyNetworks.ai_speaker.UsesCase.config.md5()

    //registration action
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
                userUTC = 1,
                returnurl = ""
            )
            isRegisterTrue = response
            println(response)

        } catch (e: HttpException) {
            println("Error:  ${e.code()}")
        }
        return isRegisterTrue
    }

    //remember password action
    suspend fun rememberPassword (
        rememberPassword: RememberPassword
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
            .addConverterFactory(ScalarsConverterFactory.create())

            .client(okHttpClient)
            .build()

        val servise = retrofit.create(ApiService::class.java)
        try {
            val response = servise.setRemeberPassword(
                thisprojectid = 11,
                this_http_host = "qaim.me",
                rememberemail = rememberPassword.remeberPassword.text.toString(),
                lng = lng,
                checkID = 1,
                userUTC = 1,
            )
            isRegisterTrue = response
            println(response)

        } catch (e: HttpException) {
            println("Error:  ${e.code()}")
        }
        return isRegisterTrue
    }

    //login action
    suspend fun login (
        loginUser: LoginUser
    ) : String {

        var isLoginTrue = ""

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://qwertynetworks.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()

        val servise = retrofit.create(ApiService::class.java)

        val passwordMD5 = md5.md5(loginUser.password.text.toString())

        try {
            val response = servise.setLogin(
                thisprojectid = 11,
                loginemail = loginUser.email.text.toString(),
                loginpassword = passwordMD5,
                lng = lng,
                applogin = "ok",
                checkID = 1,
                userUTC = 1,
            )
            isLoginTrue = response
            println(response)

        } catch (e: HttpException) {
            println("Error:  ${e.code()}")
        }
        return isLoginTrue
    }
}