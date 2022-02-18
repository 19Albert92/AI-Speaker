package com.QwertyNetworks.ai_speaker.db

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.QwertyNetworks.ai_speaker.MainActivity
import com.QwertyNetworks.ai_speaker.MyClass
import com.QwertyNetworks.ai_speaker.UsesCase.models.RegistrationUser
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.util.*

class SaveUser(val context: Context) {


    var reg_task = 1
    var preferenceHelper: PreferenceHelper = PreferenceHelper(context)

    @Throws(IOException::class, JSONException::class)
    fun register(registrationUser: RegistrationUser, dataBirth: String) {

        val textSplit = dataBirth.split('/')
        val lang = Locale.getDefault().language
        var registerUrl = "https://qwertynetworks.com/register.php"

        showSimpleProgressDialog(context, "", "Loading..", false)
        try {
            Fuel.post(registerUrl, listOf(
                "thisprojectid" to 11,
                "this_http_host" to "qaim.me",
                "blogname" to "registrationonly",
                "lng" to lang,
                "email" to registrationUser.email.text.toString(),
                "mailretry" to 0,
                "password" to "",
                "yourname" to registrationUser.name.text.toString(),
                "surname" to registrationUser.lastName.text.toString(),
                "birthdateday" to textSplit[0],
                "birthdatemonth" to textSplit[1],
                "birthdateyear" to textSplit[2],
                "checkID" to "1",
                "userUTC" to "3",
                "returnurl" to "awd"
            )).responseJson{request, response, result ->
                Log.d(Constance.LOG_TAG, "result: ${result.get().content}")
                onTaskCompleated(result.get().content, reg_task)
            }.headers
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onTaskCompleated(response: String, task: Int) {
        Log.d(Constance.LOG_TAG, response)
        removeSimpleProgress()
        when(task) {
            reg_task -> if(isSuccess(response)) {
                saveInfo(response)
                Toast.makeText(context, "Registered Succeessfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(MyClass.activity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                MyClass.activity?.startActivity(intent)
            }
        }
    }

    private fun saveInfo(response: String) {
        preferenceHelper = PreferenceHelper(context)
        preferenceHelper.putIsLogin(true)
        try {
            val jsonObject = JSONObject(response)
            if(jsonObject.getString("status") == "true") {
                val dataArray = jsonObject.getJSONArray("data")
                for (i in 0 until dataArray.length()) {
                    val dataObj = dataArray.getJSONObject(i)
                    preferenceHelper.putName(dataObj.getString("name"))
                    preferenceHelper.putEmail(dataObj.getString("email"))
                    preferenceHelper.putLastName(dataObj.getString("lastName"))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun isSuccess(response: String): Boolean {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.optString("status") == "true"
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return false
    }

    private fun getErrorMessage(response: String): String {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.getString("message")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return "No data"
    }

    private fun showSimpleProgressDialog(context: Context, title: String, msg: String, inCancel: Boolean) {
        try {
//            if(progressDialog == null) {
//                progressDialog = ProgressDialog.show(context, title, msg)
//                progressDialog?.setCancelable(inCancel)
//            }
//            if(!progressDialog!!.isShowing) {
//                progressDialog?.show()
//            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: RuntimeException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun removeSimpleProgress() {
        try {
//            if(progressDialog != null) {
//                if(progressDialog!!.isShowing) {
//                    progressDialog!!.dismiss()
//
//                }
//            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}