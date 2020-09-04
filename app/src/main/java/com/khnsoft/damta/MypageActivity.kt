package com.khnsoft.damta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_mypage.*

class MypageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        btn_back.setOnClickListener {
            finish()
        }

        btn_profile.setOnClickListener {
            val intent = Intent(this@MypageActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        btn_bookmark.setOnClickListener {
            val intent = Intent(this@MypageActivity, BookmarkActivity::class.java)
            startActivity(intent)
        }

        btn_review.setOnClickListener {
            val intent = Intent(this@MypageActivity, ReviewActivity::class.java)
            startActivity(intent)
        }

        btn_settings.setOnClickListener {
            val intent = Intent(this@MypageActivity, SettingsActivity::class.java)
            startActivity(intent)
        }

        btn_service.setOnClickListener {
            val intent = Intent(this@MypageActivity, ServiceActivity::class.java)
            startActivity(intent)
        }
    }
}