package com.khnsoft.damta

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import net.daum.mf.map.api.MapView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            var info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                MyLogger.i("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e : PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e : NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }
}