package com.example.learnprogramming

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.learnprogramming.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val isLogin = sharedPreferences.getBoolean("user_login",false)

        Handler().postDelayed(Runnable {
            try {
                if (isLogin) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
            catch (e:Exception)
            {
                Log.d("problem", "there is problem in handling login logout ${e.toString()}")
            }

        },2500)
    }
}