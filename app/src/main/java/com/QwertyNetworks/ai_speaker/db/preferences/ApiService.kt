package com.QwertyNetworks.ai_speaker.db.preferences

import com.github.kittinunf.fuel.android.core.Json
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Headers("User-Agent: Mozilla/4.0 (compatible; Universion/1.0; +https://qwertynetworks.com)",
                     "Content-Type: application/x-www-form-urlencoded")
    @POST("/register.php")
//    @POST("/api/test_post")
//    suspend fun createEmployee(@Body requestBody: RequestBody): com.QwertyNetworks.ai_speaker.db.preferences.Response
    suspend fun createEmployee(@Body requestBody: RequestBody): Response<Unit>
//    suspend fun createEmployee(@Body requestBody: RequestBody): String

    @FormUrlEncoded
    @POST("/register.php")
//    @POST("/api/test_post")
    @Headers("User-Agent: Mozilla/4.0 (compatible; Universion/1.0; +https://qwertynetworks.com)",
                     "Content-Type: application/x-www-form-urlencoded")
    suspend fun setRegister(
    @Field("thisprojectid") thisprojectid: Int,
    @Field("this_http_host") this_http_host: String,
    @Field("lng") lng: String,
    @Field("email") email: String,
    @Field("mailretry") mailretry: String,
    @Field("password") password: String,
    @Field("yourname") yourname: String,
    @Field("surname") surname: String,
    @Field("birthdateday") birthdateday: Int,
    @Field("birthdatemonth") birthdatemonth: Int,
    @Field("birthdateyear") birthdateyear: Int,
    @Field("checkID") checkID: Int,
    @Field("userUTC") userUTC: Int,
    @Field("returnurl") returnurl: String
//    ): Any
    ): String
}
