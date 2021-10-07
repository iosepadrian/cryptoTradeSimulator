package com.example.myapplication.ui.activities

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.myapplication.R


@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreenAcivity : AppCompatActivity() {

    private var progressBar: ProgressBar? = null

    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen_acivity)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        putAnimation()
        putProgress()
        startApp()
    }


    @SuppressLint("ObjectAnimatorBinding")
    private fun putProgress(){
        progressBar = findViewById(R.id.splash_screen_progress_bar)
        progressBar!!.max = 100
        val currentProgress = 100
        ObjectAnimator.ofInt(progressBar, "progress", currentProgress)
            .setDuration(4000)
            .start()
    }

    private fun startApp(){
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
    private fun putAnimation(){
        val backgroundImage: ImageView = findViewById(R.id.SplashScreenImage)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        backgroundImage.startAnimation(slideAnimation)
    }
}