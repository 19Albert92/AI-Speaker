package com.QwertyNetworks.ai_speaker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.QwertyNetworks.ai_speaker.db.PreferenceHelper
import com.QwertyNetworks.ai_speaker.ui.LoginActivity
import com.QwertyNetworks.ai_speaker.ui.ShowNextActivity


class MyClass{
    companion object{
        @SuppressLint("StaticFieldLeak") var activity: Activity? = null
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var preference: PreferenceHelper
    private val regTask = 1
    private lateinit var progressDialog: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        MyClass.activity = this@MainActivity

        //инициализация сохранения
        preference = PreferenceHelper(this)

//        if(!preference.getIsLogin()) {
//            val intent = Intent(this, ShowNextActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//            this.finish()
//        } else {
//            if (savedInstanceState == null) {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, MainFragment.newInstance())
//                    .commitNow()
//            }
//        }

//        if (savedInstanceState == null) {
//            val tag = "fragment_1"
//            showFragmentsAdd(MainFragment.newInstance(), tag)
//        }
        val intent = Intent(this, ShowNextActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            //выход из своего аккаунта
            R.id.logOut -> {
                preference.putIsLogin(false)
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showFragmentsAdd(fragment: Fragment, tag: String){
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment)
            .addToBackStack(tag)
            .commit()
    }
}