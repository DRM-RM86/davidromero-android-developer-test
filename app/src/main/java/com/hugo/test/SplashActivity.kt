package com.hugo.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.airbnb.lottie.utils.Utils
import com.hugo.test.utils.DateUtils
import com.hugo.test.utils.Navigation

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        launchMain()
    }

    private fun launchMain(){
        Handler(Looper.getMainLooper()).postDelayed({
            Navigation.goToHome(this)
            finish()
        }, 2000)
    }
}