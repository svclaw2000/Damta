package com.khnsoft.damta.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper {
    companion object {
        const val SP_NAME = "Damta"
        const val SP_SAVE_USERNAME = "save_username"
        const val SP_AUTO_LOGIN = "auto_login"
        const val SP_USERNAME = "username"
        const val SP_USER_ID = "user_id"

        fun getSharedPreferences(context: Context) : SharedPreferences{
            return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }

        fun getEditor(context: Context) : SharedPreferences.Editor {
            return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit()
        }
    }
}