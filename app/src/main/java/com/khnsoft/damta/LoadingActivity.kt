package com.khnsoft.damta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_loading.*

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val animText = AnimationUtils.loadAnimation(applicationContext, R.anim.loading_text)
        val animLogo = AnimationUtils.loadAnimation(applicationContext, R.anim.loading_logo)
        animLogo.setAnimationListener(animListener)

        loading_text.startAnimation(animText)
        loading_logo.startAnimation(animLogo)
    }

    val animListener = object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {
        }

        override fun onAnimationEnd(p0: Animation?) {
            startMain()
        }

        override fun onAnimationStart(p0: Animation?) {
        }

    }

    private fun startMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}