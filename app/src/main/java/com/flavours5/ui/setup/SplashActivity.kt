package com.flavours5.ui.setup

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.flavours5.R
import com.flavours5.common.ClsGeneral
import com.flavours5.ui.main.HomeActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var options: ActivityOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
        options = ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)

        val loginStatus: String = ClsGeneral.getPreferences(this@SplashActivity, "username").toString()
        val skip: String = ClsGeneral.getPreferences(this@SplashActivity, "skip").toString()

        Handler().postDelayed({
            if (loginStatus != null && loginStatus.isNotEmpty()) {
                val i = Intent(applicationContext, HomeActivity::class.java)
                startActivity(i, options.toBundle())
                finish()

            } else if(skip != null && skip.isNotEmpty()){
                val i = Intent(applicationContext, HomeActivity::class.java)
                startActivity(i, options.toBundle())
                finish()
            }
            else {
                val i = Intent(applicationContext, LoginActivity::class.java)
                startActivity(i, options.toBundle())
                finish()
            }
            finish()
        }, 2000)

    }
}
