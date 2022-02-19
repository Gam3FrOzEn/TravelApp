package org.techive.travelapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import org.techive.travelapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()

        Handler(Looper.getMainLooper())
            .postDelayed({
                Intent(this, MainActivity::class.java).also { startActivity(it) }
                finish()
            }, 2000)
    }
}