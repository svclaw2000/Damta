package com.khnsoft.damta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_loading.*
import java.util.ArrayList

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
            requestPermission()
        }

        override fun onAnimationStart(p0: Animation?) {
        }
    }

    fun requestPermission() {
        TedPermission.with(this@LoadingActivity)
            .setPermissionListener(permissionListener)
            .setPermissions(android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_FINE_LOCATION)
            .check()
    }

    var permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val intent = Intent(this@LoadingActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            finish()
        }
    }
}