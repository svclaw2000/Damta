package com.khnsoft.damta

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.khnsoft.damta.data.Area
import com.khnsoft.damta.data.Image
import com.khnsoft.damta.data.User
import com.khnsoft.damta.utils.AreaType
import com.khnsoft.damta.utils.SDF
import com.khnsoft.damta.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_loading.*

class LoadingActivity : AppCompatActivity() {
    companion object {
        const val SP_INIT = "init"
    }

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
        override fun onAnimationRepeat(anim: Animation?) {
        }

        override fun onAnimationEnd(anim: Animation?) {
            requestPermission()
        }

        override fun onAnimationStart(anim: Animation?) {
        }
    }

    fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setPermissions(
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ).check()
    }

    var permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val sp = SharedPreferencesHelper.getSharedPreferences(this@LoadingActivity)
            if (!sp.getBoolean(SP_INIT, false)) {
                init()
                val editor = SharedPreferencesHelper.getEditor(this@LoadingActivity)
                editor.putBoolean(SP_INIT, true)
                editor.commit()
            }
            val intent = Intent(this@LoadingActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            finish()
        }
    }

    private fun init() {
        Area(
            name = "하나은행 본사",
            type = AreaType.CLOSED,
            address = "서울 중구 을지로1가 101-1",
            x = 126.981866951611,
            y = 37.566491371702,
            ashtray = true,
            vent = true,
            density = 3.0
        ).add(this@LoadingActivity)

        val exId = Area(
            name = "을지로입구역 8번 출구",
            type = AreaType.CLOSED,
            address = "서울 중구 을지로1가",
            x = 126.9821161188,
            y = 37.5658679204824,
            ashtray = true,
            vent = true,
            density = 3.0
        ).add(this@LoadingActivity)
        val ex = Area.getById(this@LoadingActivity, exId ?: return)

        Area(
            name = "원조갈매기집",
            type = AreaType.CLOSED,
            address = "서울 중구 을지로3길 19",
            x = 126.98151821403526,
            y = 37.56694541956198,
            ashtray = true,
            vent = true,
            density = 3.0
        ).add(this@LoadingActivity)

        Area(
            name = "MITbar",
            type = AreaType.OPENED,
            address = "서울 중구 다동 116",
            x = 126.981884986965,
            y = 37.5668013170673,
            ashtray = true,
            vent = true,
            density = 3.0
        ).add(this@LoadingActivity)

        Area(
            name = "아이러브펍",
            type = AreaType.CLOSED,
            address = "서울 중구 다동 70",
            x = 126.981612985091,
            y = 37.568203224466,
            ashtray = true,
            vent = true,
            density = 3.0
        ).add(this@LoadingActivity)

        Area(
            name = "세븐일레븐 무교 3호점",
            type = AreaType.OPENED,
            address = "서울 중구 다동 92",
            x = 126.981866617006,
            y = 37.567871697275,
            ashtray = true,
            bench = true,
            vent = true,
            density = 3.0
        ).add(this@LoadingActivity)

        Area(
            name = "국민맥주",
            type = AreaType.OPENED,
            address = "서울 중구 다동 53-8",
            x = 126.980935051829,
            y = 37.5678472236303,
            ashtray = true,
            bench = true,
            vent = true,
            density = 3.0
        ).add(this@LoadingActivity)

        Area(
            name = "커피빈 을지로입구역점",
            type = AreaType.OPENED,
            address = "서울 중구 수하동 65-1",
            x = 126.98323420373534,
            y = 37.5668654870882,
            ashtray = true,
            bench = true,
            vent = true,
            density = 3.0
        ).add(this@LoadingActivity)

        Area(
            name = "혜인당구장",
            type = AreaType.CLOSED,
            address = "서울 중구 수하동 65-1",
            x = 126.98323420373534,
            y = 37.5668654870882,
            ashtray = true,
            bench = true,
            vent = true,
            machine = true,
            density = 3.0
        ).add(this@LoadingActivity)

        Area(
            name = "우리당구장",
            type = AreaType.CLOSED,
            address = "서울 중구 다동 97",
            x = 126.98147276546,
            y = 37.5676409812121,
            ashtray = true,
            bench = true,
            vent = true,
            machine = true,
            density = 3.0
        ).add(this@LoadingActivity)

        User(
            username = "hello",
            password = "1234",
            nickname = "AnNyeong",
            birthday = SDF.dateBar.parse("2000-01-12"),
            email = "svclaw2000@hanmail.net"
        ).add(this@LoadingActivity)

        val ex1 = BitmapFactory.decodeResource(resources, R.drawable.ex_1_1)
        val ex2 = BitmapFactory.decodeResource(resources, R.drawable.ex_1_2)

        Image(
            area=ex,
            image=ex1
        ).add(this@LoadingActivity)

        Image(
            area=ex,
            image=ex2
        ).add(this@LoadingActivity)
    }
}