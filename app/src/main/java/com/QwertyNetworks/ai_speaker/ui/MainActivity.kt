package com.QwertyNetworks.ai_speaker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.QwertyNetworks.ai_speaker.db.preferences.PreferencesOther
import com.QwertyNetworks.ai_speaker.ui.LoginActivity
import com.QwertyNetworks.ai_speaker.ui.ShowNextActivity
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import com.QwertyNetworks.ai_speaker.ui.main.fragments.MainFragment

    class MyClass{
        companion object{
            @SuppressLint("StaticFieldLeak") var activity: Activity? = null
        }
    }

    val preferencesOther = PreferencesOther()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        MyClass.activity = this@MainActivity

        val pref = getSharedPreferences("User_information", Context.MODE_PRIVATE)
        val state = pref?.getString(Constance.USER_ID_KEY, "").toString()

        if(state == "") {
            val intent = Intent(this, ShowNextActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

            println("this saving to shared: $state")
        } else {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
                println("this saving to shared: $state")
            }
        }
    }

    fun showFragmentsAdd(fragment: Fragment, tag: String){
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment)
            .addToBackStack(tag)
            .commit()
    }
}