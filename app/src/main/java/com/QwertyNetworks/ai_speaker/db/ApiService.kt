package com.QwertyNetworks.ai_speaker.db

import retrofit2.http.*

interface ApiService {

    //registration
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

    //remember password
    @FormUrlEncoded
    @POST("/register.php")
    @Headers("User-Agent: Mozilla/4.0 (compatible; Universion/1.0; +https://qwertynetworks.com)",
        "Content-Type: application/x-www-form-urlencoded")
    suspend fun setRemeberPassword(
        @Field("thisprojectid") thisprojectid: Int,
        @Field("this_http_host") this_http_host: String,
        @Field("rememberemail") rememberemail: String,
        @Field("lng") lng: String,
        @Field("checkID") checkID: Int,
        @Field("userUTC") userUTC: Int,
//    ): Any
    ): String

    //login
    @FormUrlEncoded
    @POST("/register.php")
    @Headers("User-Agent: Mozilla/4.0 (compatible; Universion/1.0; +https://qwertynetworks.com)",
        "Content-Type: application/x-www-form-urlencoded")
    suspend fun setLogin(
        @Field("thisprojectid") thisprojectid: Int,
        @Field("loginemail") loginemail: String,
        @Field("loginpassword") loginpassword: String,
        @Field("lng") lng: String,
        @Field("applogin") applogin: String,
        @Field("checkID") checkID: Int,
        @Field("userUTC") userUTC: Int,
//    ): Any
    ): String

    //login
    @FormUrlEncoded
    @POST("/aiexists.php")
    @Headers("User-Agent: Mozilla/4.0 (compatible; Universion/1.0; +https://qwertynetworks.com)",
        "Content-Type: application/x-www-form-urlencoded")
    suspend fun setValidNameAI(
        @Field("bot_id") bot_id: String
    ): String
}
