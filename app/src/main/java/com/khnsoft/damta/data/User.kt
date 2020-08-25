package com.khnsoft.damta.data

import android.content.Context
import com.google.gson.JsonObject
import com.khnsoft.damta.utils.DatabaseHandler
import com.khnsoft.damta.utils.SDF
import java.util.*

class User(
    var id: Int = -1,
    var username: String = "",
    var password: String = "",
    var nickname: String = "",
    var birthday: Date? = null,
    var email: String = "",
    var createdDate: Date? = null
) {

    companion object {
        var current : User? = null

        fun getById(context: Context, id: Int): User? {
            return DatabaseHandler.getUserById(context, id)
        }

        fun getByUsername(context: Context, username: String): User? {
            return DatabaseHandler.getUserByUsername(context, username)
        }

        fun getFromJson(jUser: JsonObject): User {
            return User(
                id = jUser["id"].asInt,
                username = jUser["username"].asString,
                password = jUser["password"].asString,
                nickname = jUser["nickname"].asString,
                birthday = SDF.dateBar.parse(jUser["birthday"].asString),
                email = jUser["email"].asString,
                createdDate = SDF.dateBar.parse(jUser["created_date"].asString)
            )
        }

        fun hasSameUsername(context: Context, username: String): Boolean {
            return getByUsername(context, username) != null
        }
    }

    fun add(context: Context): Boolean {
        return DatabaseHandler.addUser(context, this)
    }

    fun save(context: Context): Boolean {
        return DatabaseHandler.updateUser(context, this)
    }
}